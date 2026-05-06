import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LogIn {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(240, 242, 245));

        // Main card panel
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(197, 193, 252));
        card.setPreferredSize(new Dimension(900, 650));
        card.setBorder(new EmptyBorder(80, 100, 80, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Title
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 54));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 60, 0);
        card.add(title, gbc);

        // Username label
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(userLabel, gbc);

        // Username field
        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        userField.setPreferredSize(new Dimension(0, 60));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        card.add(userField, gbc);

        // Password label
        JLabel passLabel = new JLabel("Master Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(passLabel, gbc);

        // Password field
        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        passField.setPreferredSize(new Dimension(0, 60));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 40, 0);
        card.add(passField, gbc);

        // Button (smaller width)
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginBtn.setBackground(new Color(0, 0, 180));
        loginBtn.setForeground(Color.WHITE);

        gbc.gridy = 5;
        gbc.insets = new Insets(45, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE; // important: stops full width
        gbc.anchor = GridBagConstraints.CENTER;
        loginBtn.setPreferredSize(new Dimension(400, 80)); // smaller width
        card.add(loginBtn, gbc);

        frame.add(card);
        frame.setVisible(true);
    }
}