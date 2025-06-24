package Pharmacist;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class bill extends JFrame {
    private JTable billTable;

    private JDateChooser dateChooser;

    public bill() {
        ShowGUI();
    }

    private void viewbills() {
        try {
            Connection con = Connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bill");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("billID");
            model.addColumn("billDate");
            model.addColumn("totalPaid");
            model.addColumn("generatedBy");

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getString("billID"),
                        resultSet.getString("billDate"),
                        resultSet.getString("totalPaid"),
                        resultSet.getString("generatedBy"),
                });
            }

            billTable.setModel(model);

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
        JFrame frame = new JFrame("Bills");

        // Header label
        JLabel label = new JLabel("VIEW Bills", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 30));
        label.setBounds(0, 10, 750, 40);
        label.setForeground(new Color(0, 102, 51));

        // JTable for bills
        billTable = new JTable();
        billTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        billTable.setRowHeight(25);
        billTable.setBackground(new Color(240, 255, 240));
        billTable.setSelectionBackground(new Color(34, 139, 34));
        billTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.setBounds(50, 100, 700, 300);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(210, 255, 210));
        panel.add(scrollPane);
        panel.add(label);

        JSeparator separate = new JSeparator();
        separate.setBounds(0, 60, 800, 5);
        panel.add(separate);

        // Search field and button
        JTextField search = new JTextField();
        search.setBounds(50, 410, 150, 30);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(210, 410, 100, 30);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(34, 139, 34));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchbill(search.getText());
            }
        });

        // Date filter
        dateChooser = new JDateChooser();
        dateChooser.setBounds(330, 410, 150, 30);

        JButton filterByDateButton = new JButton("Filter by Date");
        filterByDateButton.setBounds(490, 410, 150, 30);
        filterByDateButton.setFont(new Font("Arial", Font.BOLD, 14));
        filterByDateButton.setBackground(new Color(34, 139, 34));
        filterByDateButton.setForeground(Color.WHITE);
        filterByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByDate();
            }
        });

        // Add components to panel
        panel.add(search);
        panel.add(searchButton);
        panel.add(dateChooser);
        panel.add(filterByDateButton);

        frame.add(panel);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        viewbills();
    }

    private void searchbill(String searchText) {
        DefaultTableModel model = (DefaultTableModel) billTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        billTable.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    private void filterByDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dateChooser.getDate());

        DefaultTableModel model = (DefaultTableModel) billTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        billTable.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter(date));
    }

    public static void main(String[] args) {
        new bill();
    }
}
