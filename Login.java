import javax.swing.*;
import java.awt.*;

public class FirstPageUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Password Manager");

        // Main Panel with padding (border spacing)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // top, left, bottom, right

        // Title
        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // Username
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        // Master Password
        JLabel passLabel = new JLabel("Master Password:");
        JPasswordField passField = new JPasswordField();

        // Button
        JButton loginBtn = new JButton("Login");

        // Add components
        panel.add(title);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);

        // Frame settings
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // center screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}