import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
}