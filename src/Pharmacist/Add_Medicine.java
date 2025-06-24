package Pharmacist;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Medicine {

    private JFrame frame;
    private JTextField tf1, tf2, tf3, tf4, tf5, tf6;
    private JDateChooser MFG_Date;
    private JDateChooser ExpireDate;
    private JSeparator separate;
    Connection connection;
    public String numberPattern;

    public Add_Medicine() {
        GUI();
    }

    private void GUI() {
        frame = new JFrame("ADD MEDICINE");
        JLabel label = new JLabel("ADD Items", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(new Color(34, 139, 34)); // Vert foncé pour le titre

        // Text Fields
        tf1 = new JTextField();
        tf3 = new JTextField();
        tf2 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();

        // Labels
        JLabel MedicineID = new JLabel("ID", SwingConstants.CENTER);
        JLabel Name = new JLabel("Name", SwingConstants.CENTER);
        JLabel Company = new JLabel("Company Name", SwingConstants.CENTER);
        JLabel MFG = new JLabel("MFG Date", SwingConstants.CENTER);
        JLabel Quantity = new JLabel("Quantity", SwingConstants.CENTER);
        JLabel Expire_Date = new JLabel("Expire Date");
        JLabel Rack = new JLabel("Rack");
        JLabel Price = new JLabel("Price Per Unit");

        // Button
        JButton button = new JButton("Save");
        button.setBackground(new Color(34, 139, 34)); // Vert clair
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 16)); // Police 16 pour le bouton
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set font for all labels, text fields, and buttons
        Font commonFont = new Font("Arial", Font.PLAIN, 16);
        MedicineID.setFont(commonFont);
        Name.setFont(commonFont);
        Company.setFont(commonFont);
        MFG.setFont(commonFont);
        Quantity.setFont(commonFont);
        Expire_Date.setFont(commonFont);
        Rack.setFont(commonFont);
        Price.setFont(commonFont);

        tf1.setFont(commonFont);
        tf2.setFont(commonFont);
        tf3.setFont(commonFont);
        tf4.setFont(commonFont);
        tf5.setFont(commonFont);
        tf6.setFont(commonFont);

        button.setFont(commonFont);

        // Panel
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 255, 240)); // Fond vert clair

        // Title Label
        label.setBounds(0, 10, 750, 30);
        panel.add(label);

        // Separator
        separate = new JSeparator();
        separate.setBounds(0, 45, 800, 25);
        panel.add(separate);

        // Adding components with adjusted bounds
        Name.setBounds(2, 50, 100, 30);
        tf3.setBounds(30, 90, 300, 30);
        panel.add(Name);
        panel.add(tf3);

        MedicineID.setBounds(370, 50, 100, 30);
        tf1.setBounds(400, 90, 300, 30);
        panel.add(MedicineID);
        panel.add(tf1);

        Company.setBounds(380, 130, 200, 30);
        tf2.setBounds(400, 170, 300, 30);
        panel.add(Company);
        panel.add(tf2);

        MFG.setBounds(15, 130, 100, 30);
        MFG_Date = new JDateChooser();
        MFG_Date.setBounds(30, 170, 300, 30);
        panel.add(MFG);
        panel.add(MFG_Date);

        Expire_Date.setBounds(15, 210, 100, 30);
        ExpireDate = new JDateChooser();
        ExpireDate.setBounds(30, 250, 300, 30);
        panel.add(Expire_Date);
        panel.add(ExpireDate);

        Rack.setBounds(400, 210, 100, 30);
        tf5.setBounds(400, 250, 300, 30);
        panel.add(Rack);
        panel.add(tf5);

        Quantity.setBounds(5, 290, 100, 30);
        tf4.setBounds(30, 330, 300, 30);
        panel.add(Quantity);
        panel.add(tf4);

        Price.setBounds(400, 290, 100, 30);
        tf6.setBounds(400, 330, 300, 30);
        panel.add(Price);
        panel.add(tf6);

        button.setBounds(340, 390, 100, 30);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sava();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        // Frame settings
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Add_Medicine();
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

    private void sava() {
        numberPattern = "^[0-9]*$";

        String name = tf3.getText();
        SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
        Date date = MFG_Date.getDate();
        String MFG = "";
        if (date != null) {
            MFG = DF.format(MFG_Date.getDate());
        }
        Date dates = ExpireDate.getDate();
        String Ex = "";
        if (dates != null) {
            Ex = DF.format(ExpireDate.getDate());
        }
        String Price = tf6.getText();
        int shelfNumber = Integer.parseInt(tf1.getText());
        String category = tf3.getText();
        String Company = tf2.getText();
        String Rack = tf5.getText();
        String Qunt = tf4.getText();
        String medicine_id = tf1.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name is Required");
        } else if (MFG.isEmpty()) {
            JOptionPane.showMessageDialog(null, "MFG is Required");
        } else if (Rack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Rack required");
        } else if ((tf1.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Medicine ID is required");
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

                // Convert the Java date to MySQL date format
                String mysqlDate = (date != null) ? mysqlDateFormat.format(date) : "";

                PreparedStatement preparedStatement = con.prepareStatement("insert into medicine (MedicineID, Name, CompanyName, MFGDate, ExpiryDate, MG, Quantity, PricePerUnit) values (?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, medicine_id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, Company);
                preparedStatement.setString(4, MFG);
                preparedStatement.setString(5, Ex);
                preparedStatement.setString(6, Rack);
                preparedStatement.setString(7, Qunt);
                preparedStatement.setString(8, Price);

                if (isZeroQuantityRowPresent(medicine_id)) {
                    // If present, delete it
                    deleteZeroQuantityRow(medicine_id);
                }

                preparedStatement.executeUpdate();

                // Check if the added medicine is present in the out_of_stock table
                if (isMedicineInOutOfStock(medicine_id)) {
                    // If present, delete it from out_of_stock
                    deleteFromOutOfStock(medicine_id);
                }

                JOptionPane.showMessageDialog(null, "Medicine Entered Successfully");
                frame.setVisible(false);
                new Add_Medicine();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private boolean isZeroQuantityRowPresent(String medicineId) throws SQLException {
        Connection con = Connect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM medicine WHERE MedicineID = '" + medicineId + "' AND Quantity = 0");
        return rs.next(); // Return true if there is at least one row (zero quantity row is present)
    }

    private void deleteZeroQuantityRow(String medicineId) throws SQLException {
        Connection con = Connect();
        Statement st = con.createStatement();
        st.executeUpdate("DELETE FROM medicine WHERE MedicineID = '" + medicineId + "' AND Quantity = 0");
    }

    private boolean isMedicineInOutOfStock(String medicineId) throws SQLException {
        Connection con = Connect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM out_of_stock WHERE MedicineID = '" + medicineId + "'");
        return rs.next(); // Return true if there is at least one row (medicine is in out_of_stock)
    }

    private void deleteFromOutOfStock(String medicineId) throws SQLException {
        Connection con = Connect();
        Statement st = con.createStatement();
        st.executeUpdate("DELETE FROM out_of_stock WHERE MedicineID = '" + medicineId + "'");
    }
}
