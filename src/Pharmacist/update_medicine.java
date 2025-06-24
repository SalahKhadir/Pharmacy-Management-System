package Pharmacist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class update_medicine {

    private JFrame frame;
    private JTextField searchField, tf1, tf2, tf3, tf4, tf5, tf6;
    private JDateChooser MFG_Date;
    private JDateChooser ExpireDate;
    private JSeparator separate;
    Connection connection;
    public String numberPattern;

    public update_medicine() {
        GUI();
    }

    private void GUI() {
        frame = new JFrame("UPDATE MEDICINE");
        JLabel label = new JLabel("UPDATE Items", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(new Color(0, 128, 0)); // Green color

        searchField = new JTextField();
        tf1 = new JTextField();
        tf3 = new JTextField();
        tf2 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        JLabel MedicineID = new JLabel("Medicine ID", SwingConstants.CENTER);
        JLabel Name = new JLabel("Name", SwingConstants.CENTER);
        JLabel Company = new JLabel("Company Name", SwingConstants.CENTER);
        JLabel MFG = new JLabel("MFG Date", SwingConstants.CENTER);
        JLabel Quantity = new JLabel("Quantity", SwingConstants.CENTER);
        JLabel Expire_Date = new JLabel("Expiry Date");
        JLabel MG = new JLabel("Rack");

        JLabel Price = new JLabel("Price Per Unit");

        JButton searchButton = new JButton("Search");
        JButton updateButton = new JButton("Update");

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 255, 240)); // Light green background

        label.setBounds(0, 10, 750, 30);
        panel.add(label);

        separate = new JSeparator();
        separate.setBounds(0, 45, 800, 25);
        panel.add(separate);

        searchField.setBounds(30, 50, 300, 30);
        searchButton.setBounds(340, 50, 100, 30);
        panel.add(searchField);
        panel.add(searchButton);

        Name.setBounds(2, 90, 100, 30);
        tf3.setBounds(30, 130, 300, 30);
        Name.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(Name);
        panel.add(tf3);

        MedicineID.setBounds(370, 90, 100, 30);
        tf1.setBounds(400, 130, 300, 30);
        MedicineID.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(MedicineID);
        panel.add(tf1);

        Company.setBounds(380, 170, 100, 30);
        tf2.setBounds(400, 210, 300, 30);
        Company.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(Company);
        panel.add(tf2);

        MFG.setBounds(15, 170, 100, 30);
        MFG.setForeground(new Color(0, 128, 0)); // Green color for label
        MFG_Date = new JDateChooser();
        MFG_Date.setBounds(30, 210, 300, 30);
        panel.add(MFG);
        panel.add(MFG_Date);

        Expire_Date.setBounds(15, 250, 100, 30);
        Expire_Date.setForeground(new Color(0, 128, 0)); // Green color for label
        ExpireDate = new JDateChooser();
        ExpireDate.setBounds(30, 290, 300, 30);
        panel.add(Expire_Date);
        panel.add(ExpireDate);

        MG.setBounds(400, 250, 100, 30);
        tf5.setBounds(400, 290, 300, 30);
        MG.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(MG);
        panel.add(tf5);

        Quantity.setBounds(5, 330, 100, 30);
        tf4.setBounds(30, 370, 300, 30);
        Quantity.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(Quantity);
        panel.add(tf4);

        Price.setBounds(400, 330, 100, 30);
        tf6.setBounds(400, 370, 300, 30);
        Price.setForeground(new Color(0, 128, 0)); // Green color for label
        panel.add(Price);
        panel.add(tf6);

        updateButton.setBounds(340, 430, 100, 30);
        updateButton.setBackground(new Color(0, 128, 0)); // Green background for button
        updateButton.setForeground(Color.WHITE); // White text
        panel.add(updateButton);

        searchButton.setBackground(new Color(0, 128, 0)); // Green background for button
        searchButton.setForeground(Color.WHITE); // White text
        panel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMedicine();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMedicine();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void searchMedicine() {
        String searchName = searchField.getText();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter a name to search");
        } else {
            try {
                Connection con = Connect();
                PreparedStatement preparedStatement = con.prepareStatement("select * from medicine where Name = ?");
                preparedStatement.setString(1, searchName);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    tf1.setText(resultSet.getString("MedicineID"));
                    tf3.setText(resultSet.getString("Name"));
                    tf2.setText(resultSet.getString("CompanyName"));
                    MFG_Date.setDate(resultSet.getDate("MFGDate"));
                    ExpireDate.setDate(resultSet.getDate("ExpiryDate"));
                    tf5.setText(resultSet.getString("MG"));
                    tf4.setText(resultSet.getString("Quantity"));
                    tf6.setText(resultSet.getString("PricePerUnit"));
                } else {
                    JOptionPane.showMessageDialog(null, "Medicine not found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void updateMedicine() {
        numberPattern = "^[0-9]*$";

        String name = tf3.getText();
        SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
        Date date = MFG_Date.getDate();
        String MFG = (date != null) ? DF.format(MFG_Date.getDate()) : "";

        Date dates = ExpireDate.getDate();
        String Ex = (dates != null) ? DF.format(ExpireDate.getDate()) : "";

        String Price = tf6.getText();
        String medicine_id = tf1.getText();
        String Company = tf2.getText();
        String Rack = tf5.getText();
        String Qunt = tf4.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name is Required");
        } else if (MFG.isEmpty()) {
            JOptionPane.showMessageDialog(null, "MFG is Required");
        } else if (Rack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Rack is required");
        } else if (medicine_id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Medicine id is required");
        } else if (!Qunt.matches(numberPattern)) {
            JOptionPane.showMessageDialog(null, "Quantity is invalid");
        } else if (Company.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Company should not be Empty");
        } else if (Qunt.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Quantity should not be Empty");
        } else if (Price.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price Should not be Empty");
        } else if (!Price.matches(numberPattern)) {
            JOptionPane.showMessageDialog(null, "Price is invalid");
        } else if (Ex.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Expire date should not be empty");
        } else {
            try {
                Connection con = Connect();
                SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                PreparedStatement preparedStatement = con.prepareStatement("update medicine set Name=?, CompanyName=?, MFGDate=?, ExpiryDate=?, MG=?, Quantity=?, PricePerUnit=? where MedicineID=?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, Company);
                preparedStatement.setString(3, MFG);
                preparedStatement.setString(4, Ex);
                preparedStatement.setString(5, Rack);
                preparedStatement.setString(6, Qunt);
                preparedStatement.setString(7, Price);
                preparedStatement.setString(8, medicine_id);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Medicine Updated Successfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private Connection Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    public static void main(String[] args) {
        new update_medicine();
    }
}
