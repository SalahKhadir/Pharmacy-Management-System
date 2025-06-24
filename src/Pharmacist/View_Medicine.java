package Pharmacist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class View_Medicine extends JFrame {
    private JTable medicineTable;

    public View_Medicine() {
        ShowGUI();
    }

    private void viewMedicines() {
        try {
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medicine");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("MedicineID");
            model.addColumn("Name");
            model.addColumn("Company Name");
            model.addColumn("MFG Date");
            model.addColumn("Expire Date");
            model.addColumn("MG");
            model.addColumn("Quantity");
            model.addColumn("Price Per Unit");

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("MedicineID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CompanyName"),
                        resultSet.getString("MFGDate"),
                        resultSet.getString("ExpiryDate"),
                        resultSet.getString("MG"),
                        resultSet.getString("Quantity"),
                        resultSet.getString("PricePerUnit")
                });
            }

            medicineTable.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private Connection Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    private void ShowGUI() {
        JFrame frame = new JFrame("Medicine List");

        // Header label
        JLabel label = new JLabel("VIEW Medicines", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 30));
        label.setBounds(0, 10, 750, 40);
        label.setForeground(new Color(0, 102, 51));

        // JTable for medicines
        medicineTable = new JTable();
        medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        medicineTable.setRowHeight(25);
        medicineTable.setBackground(new Color(240, 255, 240));
        medicineTable.setSelectionBackground(new Color(34, 139, 34));
        medicineTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        scrollPane.setBounds(50, 100, 700, 300);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(210, 255, 210));
        panel.add(scrollPane);
        panel.add(label);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 60, 800, 5);
        panel.add(separator);

        // Refresh and Delete button
        JButton deleteButton = new JButton("Delete Medicine");
        deleteButton.setBounds(250, 410, 150, 30);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(34, 139, 34));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMedicine();
            }
        });

        panel.add(deleteButton);

        frame.add(panel);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        viewMedicines();
    }

    private void deleteMedicine() {
        DefaultTableModel model = (DefaultTableModel) medicineTable.getModel();

        int index = medicineTable.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Please select a medicine to delete.");
            return;
        }

        String id = model.getValueAt(index, 0).toString();

        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this medicine?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                Connection connection = Connect();

                PreparedStatement st = connection.prepareStatement("DELETE FROM medicine WHERE MedicineID=?");
                st.setString(1, id);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Medicine deleted successfully");

                // Refresh the table
                viewMedicines();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void main(String[] args) {
        new View_Medicine();
    }
}
