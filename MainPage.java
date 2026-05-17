import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPage 
{
    private static JPanel listPanel; // Static reference to easily refresh the UI dashboard list

    public static void main(String[] args) {

        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Force the main window to open maximized / full-screen natively
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 🔹 SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(211, 211, 255));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JLabel title = new JLabel("🔑 Password Manager");
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));

        JButton homeBtn = new JButton("🏠 Home");
        JButton addBtn = new JButton("➕ Add Password");

        JButton[] buttons = {homeBtn, addBtn};

        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(new Color(211, 211, 255));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }

        sidebar.add(title);
        sidebar.add(homeBtn);
        sidebar.add(addBtn);

        // 🔹 CONTENT AREA
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔹 LIST PANEL (to hold visual password rows)
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);

        // 💾 EXCEL INITIAL LOADING: Fetch saved passwords immediately on startup
        refreshPasswordList();

        // Default view (Home)
        content.add(scrollPane, BorderLayout.CENTER);

        // 🔥 HOME BUTTON EVENT
        homeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                content.removeAll();
                refreshPasswordList(); // Dynamic refresh from Excel when hitting home
                content.add(scrollPane, BorderLayout.CENTER);
                content.revalidate();
                content.repaint();
            }
        });

        // 🔥 ADD PASSWORD EVENT
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                content.removeAll();

                JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

                JTextField platformField = new JTextField();
                // 🔐 JPasswordField secures input text by masking it with dots natively
                JPasswordField passwordField = new JPasswordField(); 
                JButton saveBtn = new JButton("Save");

                form.add(new JLabel("Platform:"));
                form.add(platformField);
                form.add(new JLabel("Password:")); 
                form.add(passwordField);           
                form.add(new JLabel(""));
                form.add(saveBtn);

                content.add(form, BorderLayout.NORTH);

                // 🔥 SAVE BUTTON EVENT (Encrypts & pushes record to users.xlsx)
                saveBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {

                        String platform = platformField.getText().trim();
                        String rawPassword = new String(passwordField.getPassword()).trim();

                        if (!platform.isEmpty() && !rawPassword.isEmpty()) {
                            
                            // 1. 🔒 Cryptographic Step: Encrypt the password string via AESUtil
                            String encryptedPass = AESUtil.encrypt(rawPassword);

                            // 2. 💾 Persistence Step: Save records to the designated Excel sheet
                            // Username field is passed as a blank string "" since we transitioned to Platform & Password
                            boolean isSaved = ExcelHelper.savePasswordRecord(platform, "", encryptedPass);

                            if (isSaved) {
                                JOptionPane.showMessageDialog(frame, "Saved securely to Excel Database!");
                                
                                // Reset fields cleanly
                                platformField.setText("");
                                passwordField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Database Write Error!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Fill all fields!");
                        }
                    }
                });

                content.revalidate();
                content.repaint();
            }
        });

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(content, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // 🔹 REFRESH AND SYNC ALL METHOD ENTRIES FROM THE EXCEL ENGINE
    private static void refreshPasswordList() {
        listPanel.removeAll(); 

        // Call ExcelHelper method to pull decrypted wrapper elements from spreadsheet
        ArrayList<Credential> savedPasswords = ExcelHelper.loadAllPasswords();

        for (Credential cred : savedPasswords) {
            // Build out an individual visual dashboard card for every single asset inside the file
            listPanel.add(createCard(cred.website, cred.encryptedPassword));
            listPanel.add(Box.createVerticalStrut(10));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // 🔹 DYNAMIC UI CARD GENERATOR WITH EYE TOGGLE & SPREADSHEET DELETION
    private static JPanel createCard(String platform, String encryptedPassword) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
        card.setMaximumSize(new Dimension(600, 70));

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setBackground(Color.WHITE);
        
        JLabel platLabel = new JLabel("Platform: " + platform);
        platLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel passLabel = new JLabel("Password: ••••••••"); 
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        info.add(platLabel);
        info.add(passLabel);

        // UI alignment container for right-aligned interactions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        // 👁️ EYE DECRYPTION TOGGLE BUTTON
        JButton eyeBtn = new JButton("👁️");
        eyeBtn.setFocusPainted(false);
        eyeBtn.setContentAreaFilled(false);
        eyeBtn.setBorderPainted(false);
        
        eyeBtn.addActionListener(new ActionListener() {
            private boolean isRevealed = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRevealed) {
                    // Decrypt string data tracking dynamically using AESUtil
                    String decrypted = AESUtil.decrypt(encryptedPassword);
                    passLabel.setText("Password: " + decrypted);
                    isRevealed = true;
                } else {
                    passLabel.setText("Password: ••••••••");
                    isRevealed = false;
                }
            }
        });

        // ❌ RECORD DELETION BUTTON
        JButton deleteBtn = new JButton("❌");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setBorderPainted(false);
        
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(card, 
                        "Are you sure you want to delete the password for " + platform + "?", 
                        "Delete Record", JOptionPane.YES_NO_OPTION);
                        
                if (confirm == JOptionPane.YES_OPTION) {
                    // Database Layer: Wipe targeted row matching the matching platform name string
                    boolean isDeleted = ExcelHelper.deletePasswordRecord(platform, "");
                    if (isDeleted) {
                        refreshPasswordList(); // Dynamic clean interface reload context
                    } else {
                        JOptionPane.showMessageDialog(card, "Error removing record from Excel database file.");
                    }
                }
            }
        });

        actionPanel.add(eyeBtn);
        actionPanel.add(deleteBtn);

        card.add(info, BorderLayout.WEST);
        card.add(actionPanel, BorderLayout.EAST);

        return card;
    }
}