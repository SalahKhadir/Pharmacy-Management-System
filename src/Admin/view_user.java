package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class view_user extends JFrame {

    private JFrame frame;
    private JButton button;
    private JTable table;
    private JLabel label;
    private JPanel panel;
    private JSeparator separator;
    private DefaultTableModel model;
    private String username = " ";

    public view_user() {
    }

    public view_user(String tempUsername) {
        username = tempUsername;
        createGUI();
        loadData();
    }

    private void createGUI() {
        frame = new JFrame("View User");
        frame.getContentPane().setBackground(new Color(200, 255, 200)); // Background color: light green

        // Title label
        label = new JLabel("View Users", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 28)); // Font changed to Verdana
        label.setForeground(new Color(34, 139, 34)); // Dark green for the title

        separator = new JSeparator();
        separator.setForeground(new Color(34, 139, 34)); // Dark green for the separator

        // Button styling
        button = new JButton("Delete Selected User");
        button.setBackground(new Color(34, 139, 34)); // Dark green background
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("SansSerif", Font.BOLD, 16)); // Modern font for button

        // Table configuration
        String[] columnNames = {"ID", "Name", "Role", "Joining Date", "Mobile", "ID Card No", "Username", "Password", "Salary"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Font and selection color for the table
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(34, 139, 34)); // Dark green for row selection
        table.setSelectionForeground(Color.WHITE); // White text for selected rows
        table.setRowHeight(25);

        // Center-align the table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        // Positioning and layout
        label.setBounds(0, 10, 800, 40);
        button.setBounds(275, 420, 250, 40);
        scrollPane.setBounds(15, 60, 770, 350);
        separator.setBounds(0, 50, 800, 10);

        panel = new JPanel(null);
        panel.setBackground(new Color(200, 255, 200)); // Same light green background
        panel.add(button);
        panel.add(label);
        panel.add(scrollPane);
        panel.add(separator);

        button.addActionListener(e -> deleteSelectedUser());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    // Add logic if needed for a single click
                }
            }
        });

        frame.add(panel);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("username"), // Changed from "id" to "username"
                        resultSet.getString("name"),
                        resultSet.getString("user_role"),
                        resultSet.getString("Joiningdate"),
                        resultSet.getString("mobile_number"),
                        resultSet.getString("IdCardNo"),
                        resultSet.getString("username"), // This might be duplicate, consider removing
                        resultSet.getString("password"),
                        resultSet.getString("Salary")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void deleteSelectedUser() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to delete.");
            return;
        }

        String usernameToDelete = model.getValueAt(index, 0).toString(); // Get username from first column
        String usernameInTable = model.getValueAt(index, 0).toString(); // Same as above since we're using username as identifier

        if (username.equals(usernameInTable)) {
            JOptionPane.showMessageDialog(null, "You cannot delete your own account.");
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");

                    PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE username=?"); // Changed from id to username
                    st.setString(1, usernameToDelete);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "User deleted successfully");

                    // Refresh the table
                    model.removeRow(index);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }
}
