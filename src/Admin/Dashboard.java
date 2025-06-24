package Admin;
import Login.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JFrame frame;
    private JLabel label;
    private JButton addUser, bills, view, updateUser, logout, exit;
    private JPanel panel;
    JSeparator separator;
    private String username;



    public Dashboard(String user) {
        this.username = user;
        GUI();
    }

    private void GUI() {
        frame = new JFrame("Pharmacy Stock Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header label
        label = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        label.setBounds(0, 25, 1300, 50);
        label.setFont(new Font("Verdana", Font.BOLD, 32));
        label.setForeground(new Color(34, 139, 34)); // Forest Green

        separator = new JSeparator();
        separator.setBounds(0, 70, 1370, 10);
        separator.setForeground(new Color(85, 107, 47)); // Dark Olive Green

        // Add User Button
        String addUserImagePath = "src/Admin/images/add_user.png";
        ImageIcon addUserIcon = new ImageIcon(addUserImagePath);
        addUser = new JButton("ADD USER", addUserIcon);
        styleButton(addUser);
        addUser.setBounds(300, 150, 300, 100);

        // Bills Button
        String billsImagePath = "src/Admin/images/bills.jpg";
        ImageIcon billsIcon = new ImageIcon(billsImagePath);
        bills = new JButton("BILLS", billsIcon);
        styleButton(bills);
        bills.setBounds(700, 150, 300, 100);

        // View Button
        String viewImagePath = "src/Admin/images/view.jpg";
        ImageIcon viewIcon = new ImageIcon(viewImagePath);
        view = new JButton("VIEW", viewIcon);
        styleButton(view);
        view.setBounds(300, 300, 300, 100);

        // Update User Button
        String updateUserImagePath = "src/Admin/images/update.png";
        ImageIcon updateUserIcon = new ImageIcon(updateUserImagePath);
        updateUser = new JButton("UPDATE", updateUserIcon);
        styleButton(updateUser);
        updateUser.setBounds(700, 300, 300, 100);

        // Logout Button
        String logoutImagePath = "src/Admin/images/logout.png";
        ImageIcon logoutIcon = new ImageIcon(logoutImagePath);
        logout = new JButton("LOGOUT", logoutIcon);
        styleButton(logout);
        logout.setBounds(300, 450, 300, 100);

        // Exit Button
        String exitImagePath = "src/Admin/images/exit.png";
        ImageIcon exitIcon = new ImageIcon(exitImagePath);
        exit = new JButton("EXIT", exitIcon);
        styleButton(exit);
        exit.setBounds(700, 450, 300, 100);

        // Panel setup
        panel = new JPanel(null);
        panel.add(addUser);
        panel.add(separator);
        panel.add(bills);
        panel.add(view);
        panel.add(updateUser);
        panel.add(logout);
        panel.add(exit);
        panel.setBackground(new Color(240, 255, 240)); // Honeydew

        frame.add(label, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.CENTER);

        // Action listeners
        addUser.addActionListener(e -> new Add_user());
        view.addActionListener(e -> new view_user(username));
        updateUser.addActionListener(e -> new Update_user());
        bills.addActionListener(e -> new bills());
        logout.addActionListener(e -> {
            int a = JOptionPane.showConfirmDialog(null, "Do you want to Logout", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                frame.setVisible(false);
                new User();
            }
        });
        exit.addActionListener(e -> {
            int a = JOptionPane.showConfirmDialog(null, "Do you want to Exit", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                System.exit(0);
            }
        });

        frame.setSize(1368, 766);
        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(34, 139, 34)); // Forest Green
        button.setFont(new Font("Verdana", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(85, 107, 47), 2)); // Dark Olive Green border
    }
}
