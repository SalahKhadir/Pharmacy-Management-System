package Login;

import Admin.Dashboard;
import Pharmacist.Pharmacist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class User {
    Connection connection;
    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JLabel titleLabel, forgotPasswordLabel, usernameLabel, passwordLabel;
    JPanel mainPanel, imagePanel, formPanel;

    public User() {
        createLoginGUI();
    }

    void createLoginGUI() {
        frame = new JFrame("Pharmacy Management System");
        frame.setSize(750, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());

        // Left Image Panel
        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(34, 139, 34)); // Green background
        imagePanel.setLayout(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon("C:/Users/lenovo/Desktop/bb.png")); // Replace with your image path
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Right Form Panel
        formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel("Welcome Back!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 139, 34));
        titleLabel.setBounds(50, 30, 300, 30);

        JLabel subtitleLabel = new JLabel("Sign in to access your account.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setBounds(50, 60, 300, 20);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(50, 120, 150, 30);
        usernameField = new JTextField();
        usernameField.setBounds(50, 150, 300, 30);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(50, 200, 150, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 230, 300, 30);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));

        loginButton = new JButton("Sign In");
        loginButton.setBounds(50, 290, 300, 40);
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                authenticateUser(username, password);
            }
        });

        forgotPasswordLabel = new JLabel("Password or username oublié");
        forgotPasswordLabel.setForeground(new Color(34, 139, 34));
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBounds(50, 340, 300, 20);
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "Please contact your Admin.");
            }
        });

        formPanel.add(titleLabel);
        formPanel.add(subtitleLabel);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(loginButton);
        formPanel.add(forgotPasswordLabel);

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void authenticateUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("user_role");
                frame.setVisible(false);
                if (role.equals("Admin")) {
                    new Dashboard(username);
                } else if (role.equals("Pharmacist")) {
                    new Pharmacist(username);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }
}
