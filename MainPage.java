import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage 
{
    public static void main(String[] args) {

        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // 🔹 LIST PANEL (to store cards)
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);

        // Default view (Home)
        content.add(scrollPane, BorderLayout.CENTER);

        // 🔥 HOME BUTTON EVENT
        homeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                content.removeAll();
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
                JTextField userField = new JTextField();
                JButton saveBtn = new JButton("Save");

                form.add(new JLabel("Platform:"));
                form.add(platformField);
                form.add(new JLabel("Username:"));
                form.add(userField);
                form.add(new JLabel(""));
                form.add(saveBtn);

                content.add(form, BorderLayout.NORTH);

                // 🔥 SAVE BUTTON EVENT
                saveBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {

                        String platform = platformField.getText();
                        String user = userField.getText();

                        if (!platform.isEmpty() && !user.isEmpty()) {

                            listPanel.add(createCard(platform, user));
                            listPanel.add(Box.createVerticalStrut(10));

                            listPanel.revalidate();
                            listPanel.repaint();

                            JOptionPane.showMessageDialog(frame, "Saved!");

                            // Clear fields
                            platformField.setText("");
                            userField.setText("");
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

    // 🔹 CARD UI
    private static JPanel createCard(String platform, String user) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(500, 70));

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setBackground(Color.WHITE);
        info.add(new JLabel(platform));
        info.add(new JLabel(user));

        card.add(info, BorderLayout.WEST);
        card.add(new JLabel("••••••"), BorderLayout.EAST);

        return card;
    }
}