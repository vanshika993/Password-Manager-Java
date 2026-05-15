import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;

public class LogIn
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(240, 242, 245));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(197, 193, 252));
        card.setPreferredSize(new Dimension(900, 650));
        card.setBorder(new EmptyBorder(80, 100, 80, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 54));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 60, 0);
        card.add(title, gbc);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(userLabel, gbc);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        userField.setPreferredSize(new Dimension(0, 60));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        card.add(userField, gbc);

        JLabel passLabel = new JLabel("Master Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        passField.setPreferredSize(new Dimension(0, 60));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 40, 0);
        card.add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginBtn.setBackground(new Color(0, 0, 180));
        loginBtn.setForeground(Color.WHITE);

        gbc.gridy = 5;
        gbc.insets = new Insets(45, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginBtn.setPreferredSize(new Dimension(400, 80));
        card.add(loginBtn, gbc);

        
        loginBtn.addActionListener(e -> {
    String username = userField.getText();
    String password = new String(passField.getPassword());

     
    if (ExcelHelper.validateUser(username, password)) {
        JOptionPane.showMessageDialog(frame, "Login Successful!");
        frame.dispose();  
        MainPage.main(null); 
    } else {
        JOptionPane.showMessageDialog(frame, "Invalid Credentials");
    }
});

        frame.add(card);
        frame.setVisible(true);
    }

     
    public static boolean checkLogin(String user, String pass) {
        try {
            FileInputStream file = new FileInputStream("users.xlsx");
            Workbook wb = WorkbookFactory.create(file);
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                Cell userCell = row.getCell(0);
                Cell passCell = row.getCell(1);

                if (userCell != null && passCell != null) {
                    String dbUser = userCell.getStringCellValue();
                    String dbPass = passCell.getStringCellValue();

                    if (user.equals(dbUser) && pass.equals(dbPass)) {
                        wb.close();
                        return true;
                    }
                }
            }

            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}