import javax.swing.*;
import java.awt.*;

public class Login {
    
    public static void main(String[] args) {

        JFrame frame = new JFrame("Password Manager");
        frame.setSize(1400, 800); // Desktop size
        frame.setLayout(null);    // IMPORTANT: enables setBounds
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title
        JLabel title = new JLabel("Login");
        title.setBounds(650, 100, 200, 40);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(550, 200, 100, 30);

        JTextField userField = new JTextField();
        userField.setBounds(650, 200, 200, 30);

        // Master Password
        JLabel passLabel = new JLabel("Master Password:");
        passLabel.setBounds(520, 260, 130, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(650, 260, 200, 30);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(650, 320, 100, 35);

        // Add components
        frame.add(title);
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);

        frame.setVisible(true);
    }
}