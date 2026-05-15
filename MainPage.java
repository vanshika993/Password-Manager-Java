import javax.swing.*;
import java.awt.*;

public class MainPage 
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
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
        for (JButton btn : buttons) 
        {
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setBackground(new Color(211, 211, 255));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }
        sidebar.add(title);
        sidebar.add(homeBtn);
        sidebar.add(addBtn);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(content, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private static JPanel createCard(String platform, String user) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
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