import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    JFrame frame;
    CardLayout cardLayout;
    JPanel mainPanel;

    // Store user credentials (for demo)
    String savedUsername = "";
    String savedPassword = "";

    public Main() {
        frame = new JFrame("Password Manager");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "Login");
        mainPanel.add(signupPanel(), "Signup");
        mainPanel.add(dashboardPanel(), "Dashboard");

        frame.add(mainPanel);
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 🔹 LOGIN PANEL
    JPanel loginPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Go to Signup");

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals(savedUsername) && pass.equals(savedPassword)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials");
            }
        });

        signupBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Signup");
        });

        return panel;
    }

    // 🔹 SIGNUP PANEL
    JPanel signupPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));

        JTextField newUser = new JTextField();
        JPasswordField newPass = new JPasswordField();

        JButton createBtn = new JButton("Create Account");
        JButton backBtn = new JButton("Back to Login");

        panel.add(new JLabel("New Username:"));
        panel.add(newUser);
        panel.add(new JLabel("Master Password:"));
        panel.add(newPass);
        panel.add(createBtn);
        panel.add(backBtn);

        createBtn.addActionListener(e -> {
            if (newUser.getText().isEmpty() || newPass.getPassword().length == 0) {
                JOptionPane.showMessageDialog(frame, "Fill all fields!");
                return;
            }

            savedUsername = newUser.getText();
            savedPassword = new String(newPass.getPassword());

            JOptionPane.showMessageDialog(frame, "Account Created!");
            cardLayout.show(mainPanel, "Login");
        });

        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Login");
        });

        return panel;
    }

    // 🔹 DASHBOARD PANEL
    JPanel dashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // List to store passwords
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> passwordList = new JList<>(model);

        JTextField siteField = new JTextField();
        JTextField passField = new JTextField();

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton logoutBtn = new JButton("Logout");

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Website:"));
        inputPanel.add(siteField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passField);
        inputPanel.add(addBtn);
        inputPanel.add(deleteBtn);
        inputPanel.add(logoutBtn);

        panel.add(new JScrollPane(passwordList), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add entry
        addBtn.addActionListener(e -> {
            String site = siteField.getText();
            String pass = passField.getText();

            if (!site.isEmpty() && !pass.isEmpty()) {
                model.addElement(site + " : " + pass);
                siteField.setText("");
                passField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Enter all fields!");
            }
        });

        // Delete entry
        deleteBtn.addActionListener(e -> {
            int index = passwordList.getSelectedIndex();
            if (index != -1) {
                model.remove(index);
            } else {
                JOptionPane.showMessageDialog(frame, "Select an item to delete");
            }
        });

        // Logout
        logoutBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Login");
        });

        return panel;
    }

    public static void main(String[] args) {
        new Main();
    }
}