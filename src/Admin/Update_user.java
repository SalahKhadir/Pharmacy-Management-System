package Admin;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Update_user {
    private JFrame frame;
    private JComboBox<String> comboBox;
    private JTextField tf1, tf2, tf3, tf4, tf5, tf6;
    private JDateChooser dateChooser;
    private int checkUser;


    public Update_user() {
        GUI();
    }

    private void GUI() {
        frame = new JFrame("Update User");
        frame.getContentPane().setBackground(Color.decode("#F0FFF0"));

        JLabel label = new JLabel("Update User");
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.decode("#228B22"));

        comboBox = new JComboBox<>(new String[]{"Admin", "Pharmacist"});
        tf1 = createTextField();
        tf2 = createTextField();
        tf3 = createTextField();
        tf4 = createTextField();
        tf5 = createTextField();
        tf6 = createTextField();

        JLabel userRole = createLabel("User Role");
        JLabel emailLabel = createLabel("ID Card No");
        JLabel nameLabel = createLabel("Name");
        JLabel usernameLabel = createLabel("Username");
        JLabel dobLabel = createLabel("Joining Date");
        JLabel addressLabel = createLabel("Address");
        JLabel passwordLabel = createLabel("Password");
        JLabel mobileLabel = createLabel("Mobile");

        JSeparator separate = new JSeparator();
        separate.setBounds(0, 45, 800, 25);
        separate.setForeground(Color.decode("#228B22"));

        JButton searchButton = createButton("Search");
        JButton updateButton = createButton("Update");

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#F0FFF0"));

        label.setBounds(300, 10, 750, 30);
        panel.add(label);
        panel.add(separate);

        placeComponents(panel, usernameLabel, tf2, searchButton, 100, 50, 490, 50);
        placeComponents(panel, userRole, comboBox, null, 30, 80, 30, 120);
        placeComponents(panel, emailLabel, tf1, null, 400, 80, 400, 120);
        placeComponents(panel, nameLabel, tf3, null, 30, 150, 30, 190);
        placeComponents(panel, dobLabel, null, null, 400, 150, 400, 190);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(400, 190, 300, 30);
        panel.add(dateChooser);

        placeComponents(panel, passwordLabel, tf5, null, 400, 230, 400, 270);
        placeComponents(panel, addressLabel, tf4, null, 30, 230, 30, 270);
        placeComponents(panel, mobileLabel, tf6, null, 30, 310, 30, 340);

        updateButton.setBounds(340, 390, 100, 30);
        panel.add(updateButton);

        searchButton.addActionListener(e -> Search());
        updateButton.addActionListener(e -> Update());

        // Updated frame size to 900x500 to accommodate the button and other components
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(900, 500);  // Increased width to 900
        frame.setLocationRelativeTo(null);
        frame.setResizable(true); // Allow window resizing
        frame.setVisible(true);
    }

    private Connection Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpharmacie", "root", "root");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
        }
        return null;
    }

    private void Search() {
        checkUser = 0;
        String username = tf2.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username should not be empty.");
        } else {
            try (Connection con = Connect();
                 Statement st = con.createStatement();
                 ResultSet resultSet = st.executeQuery("SELECT * FROM users WHERE username='" + username + "'")) {

                if (resultSet.next()) {
                    checkUser = 1;
                    tf2.setEditable(false);
                    tf1.setText(resultSet.getString("IdCardNo"));
                    tf3.setText(resultSet.getString("name"));
                    tf4.setText(resultSet.getString("Salary"));
                    tf5.setText(resultSet.getString("password"));
                    tf6.setText(resultSet.getString("mobile_number"));

                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("Joiningdate"));
                    dateChooser.setDate(date);

                    comboBox.setSelectedItem(resultSet.getString("user_role"));
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    private void Update() {
        String userRole = (String) comboBox.getSelectedItem();
        String name = tf3.getText();
        String idCardNo = tf1.getText();
        String password = tf5.getText();
        String mobileNumber = tf6.getText();
        String salary = tf4.getText();
        Date joiningDate = dateChooser.getDate();

        if (name.isEmpty() || idCardNo.isEmpty() || password.isEmpty() || mobileNumber.isEmpty() || salary.isEmpty() || joiningDate == null) {
            JOptionPane.showMessageDialog(null, "All fields must be filled.");
        } else if (!mobileNumber.matches("^[0-9]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid mobile number.");
        } else if (!idCardNo.matches("^[0-9]*$")) {
            JOptionPane.showMessageDialog(null, "Invalid ID card number.");
        } else {
            try (Connection con = Connect();
                 PreparedStatement pst = con.prepareStatement("UPDATE users SET user_role=?, name=?, Joiningdate=?, mobile_number=?, IdCardNo=?, password=?, Salary=? WHERE username=?")) {

                pst.setString(1, userRole);
                pst.setString(2, name);
                pst.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(joiningDate));
                pst.setString(4, mobileNumber);
                pst.setString(5, idCardNo);
                pst.setString(6, password);
                pst.setString(7, salary);
                pst.setString(8, tf2.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "User updated successfully.");
                frame.dispose();
                new Update_user();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(Color.decode("#228B22"));
        textField.setBorder(BorderFactory.createLineBorder(Color.decode("#228B22"), 1));
        return textField;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(Color.decode("#228B22"));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#228B22"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return button;
    }

    private void placeComponents(JPanel panel, JLabel label, JComponent field, JComponent extra, int x1, int y1, int x2, int y2) {
        if (label != null) {
            label.setBounds(x1, y1, 100, 30);
            panel.add(label);
        }
        if (field != null) {
            field.setBounds(x2, y2, 300, 30);
            panel.add(field);
        }
        if (extra != null) {
            extra.setBounds(x2 + 310, y2, 100, 30);
            panel.add(extra);
        }
    }

    public static void main(String[] args) {
        new Update_user();
    }
}
