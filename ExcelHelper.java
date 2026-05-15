import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ExcelHelper {
    private static final String FILE_NAME = "users.xlsx";

    // This checks the database for a matching username and password
    public static boolean validateUser(String inputUser, String inputPass) {
        File file = new File(FILE_NAME);

        // If the database doesn't exist yet, create it with a default user
        if (!file.exists()) {
            createDefaultDatabase();
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell userCell = row.getCell(0);
                Cell passCell = row.getCell(1);

                if (userCell != null && passCell != null) {
                    String dbUser = userCell.getStringCellValue();
                    String dbPass = passCell.getStringCellValue();

                    // If both match, verification passes!
                    if (inputUser.equals(dbUser) && inputPass.equals(dbPass)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper to initialize the Excel sheet if it's completely missing
    private static void createDefaultDatabase() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Credentials");
            Row defaultRow = sheet.createRow(0);
            
            // Setting a default credential so you can log in right away
            defaultRow.createCell(0).setCellValue("Vanshika");
            defaultRow.createCell(1).setCellValue("12345");

            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
            }
            System.out.println("Database created automatically with default credentials.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final String PASS_SHEET_NAME = "Passwords";

// 1. SAVE A NEW PASSWORD RECORD TO EXCEL
public static boolean savePasswordRecord(String website, String username, String encryptedPass) {
    File file = new File(FILE_NAME);
    Workbook workbook;
    Sheet sheet;

    try {
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = WorkbookFactory.create(fis);
            fis.close();
        } else {
            workbook = new XSSFWorkbook();
        }

        // Get or dynamically create the password sheet tab
        sheet = workbook.getSheet(PASS_SHEET_NAME);
        if (sheet == null) {
            sheet = workbook.createSheet(PASS_SHEET_NAME);
            // Create a quick title header row index 0
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Website");
            headerRow.createCell(1).setCellValue("Username");
            headerRow.createCell(2).setCellValue("Password");
        }

        // Append to the bottom of the spreadsheet layout
        int lastRowIndex = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowIndex + 1);
        
        newRow.createCell(0).setCellValue(website);
        newRow.createCell(1).setCellValue(username);
        newRow.createCell(2).setCellValue(encryptedPass);

        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        workbook.write(fos);
        fos.close();
        workbook.close();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// 2. READ ALL PASSWORDS FROM EXCEL INTO JAVA MEMORY
public static ArrayList<Credential> loadAllPasswords() {
    ArrayList<Credential> records = new ArrayList<>();
    File file = new File(FILE_NAME);
    if (!file.exists()) return records;

    try (FileInputStream fis = new FileInputStream(file);
         Workbook workbook = WorkbookFactory.create(fis)) {
        
        Sheet sheet = workbook.getSheet(PASS_SHEET_NAME);
        if (sheet == null) return records;

        // Loop rows skipping the header line
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell webCell = row.getCell(0);
            Cell userCell = row.getCell(1);
            Cell passCell = row.getCell(2);

            if (webCell != null && userCell != null && passCell != null) {
                records.add(new Credential(
                    webCell.getStringCellValue(),
                    userCell.getStringCellValue(),
                    passCell.getStringCellValue()
                ));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return records;
}

// 3. DELETE A TARGET RECORD FROM THE SPREADSHEET SYSTEM
public static boolean deletePasswordRecord(String website, String username) {
    File file = new File(FILE_NAME);
    if (!file.exists()) return false;

    try (FileInputStream fis = new FileInputStream(file);
         Workbook workbook = WorkbookFactory.create(fis)) {
        
        Sheet sheet = workbook.getSheet(PASS_SHEET_NAME);
        if (sheet == null) return false;

        boolean found = false;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String dbWeb = row.getCell(0).getStringCellValue();
            String dbUser = row.getCell(1).getStringCellValue();

            if (dbWeb.equals(website) && dbUser.equals(username)) {
                int lastRowNo = sheet.getLastRowNum();
                // Shift rows up to cleanly wipe out the missing index gap
                if (i < lastRowNo) {
                    sheet.shiftRows(i + 1, lastRowNo, -1);
                } else {
                    sheet.removeRow(row);
                }
                found = true;
                break;
            }
        }

        if (found) {
            try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
                workbook.write(fos);
            }
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}