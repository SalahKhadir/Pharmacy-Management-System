package Admin;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_user {

    private JFrame frame;
    public JLabel iconLabel;
    private JComboBox<String> comboBox;
    private JTextField tf1, tf2, tf3, tf4, tf5, tf6;
    private JDateChooser dateChooser;
    public String Pattern;
    public int checkusername;
    private JSeparator separate;
    Connection connection;

    public Add_user() {
        GUI();
    }

    private void GUI() {
        frame = new JFrame("Add User");
        frame.getContentPane().setBackground(new Color(255, 255, 255)); // Blanc pour le fond
        JLabel label = new JLabel("ADD USER", SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 28));
        label.setForeground(new Color(0, 128, 0)); // Vert pour le titre

        String[] roles = {"Admin", "Pharmacist"};
        comboBox = new JComboBox<>(roles);
        comboBox.setBackground(new Color(0, 128, 0)); // Vert pour le combo box
        comboBox.setForeground(Color.WHITE); // Blanc pour le texte du combo box
        tf1 = new JTextField();
        tf3 = new JTextField();
        tf2 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        JLabel userRole = new JLabel("User Role", SwingConstants.CENTER);
        separate = new JSeparator();
        JLabel emailLabel = new JLabel("ID Card No.", SwingConstants.CENTER);
        JLabel nameLabel = new JLabel("Name", SwingConstants.CENTER);
        JLabel usernameLabel = new JLabel("Username", SwingConstants.CENTER);
        JLabel dobLabel = new JLabel("Joining Date", SwingConstants.CENTER);
        JLabel addressLabel = new JLabel("Salary", SwingConstants.CENTER);
        JLabel passwordLabel = new JLabel("Password");
        JLabel mobileLabel = new JLabel("Mobile");
        iconLabel = new JLabel();
        JButton button = new JButton("Save");

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(255, 255, 255)); // Blanc pour le panneau

        label.setBounds(0, 10, 750, 30);
        panel.add(label);

        separate.setBounds(0, 45, 800, 25);
        panel.add(separate);

        userRole.setBounds(10, 50, 100, 30);
        comboBox.setBounds(30, 90, 300, 30);
        panel.add(userRole);
        panel.add(comboBox);

        emailLabel.setBounds(370, 50, 100, 30);
        tf1.setBounds(400, 90, 300, 30);
        panel.add(emailLabel);
        panel.add(tf1);

        nameLabel.setBounds(2, 130, 100, 30);
        tf3.setBounds(30, 170, 300, 30);
        panel.add(nameLabel);
        panel.add(tf3);

        usernameLabel.setBounds(380, 130, 100, 30);
        tf2.setBounds(400, 170, 300, 30);
        iconLabel.setBounds(700, 170, 100, 30);
        panel.add(usernameLabel);
        panel.add(tf2);
        panel.add(iconLabel);

        dobLabel.setBounds(15, 210, 100, 30);
        dateChooser = new JDateChooser();
        dateChooser.setBounds(30, 250, 300, 30);
        panel.add(dobLabel);
        panel.add(dateChooser);

        passwordLabel.setBounds(400, 210, 100, 30);
        tf5.setBounds(400, 250, 300, 30);
        panel.add(passwordLabel);
        panel.add(tf5);

        addressLabel.setBounds(5, 290, 100, 30);
        tf4.setBounds(30, 330, 300, 30);
        panel.add(addressLabel);
        panel.add(tf4);

        mobileLabel.setBounds(400, 290, 100, 30);
        tf6.setBounds(400, 330, 300, 30);
        panel.add(mobileLabel);
        panel.add(tf6);

        button.setBounds(340, 390, 100, 30);
        button.setBackground(new Color(0, 128, 0)); // Vert pour le bouton
        button.setForeground(Color.WHITE); // Blanc pour le texte du bouton
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        tf2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                information();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
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

    private void information() {
        checkusername = 0;

        iconLabel.setVisible(true);

        String username = tf2.getText();
        if (!username.isEmpty()) {
            iconLabel.setVisible(true);
            iconLabel.setText("");
            checkusername = 0;
            try {
                Connection con = Connect();
                Statement st = con.createStatement();
                ResultSet resultSet = st.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

                while (resultSet.next()) {
                    checkusername = 1;
                    iconLabel.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            iconLabel.setVisible(false);
        }
    }

    private void save() {
        Pattern = "^[0-9]*$";

        String user_role = (String) comboBox.getSelectedItem();
        String name = tf3.getText();
        SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateChooser.getDate();
        String Joiningdate = "";
        if (date != null) {
            Joiningdate = DF.format(dateChooser.getDate());
        }
        String MobileNum = tf6.getText();
        String IdNo = tf1.getText();
        String username = tf2.getText();
        String password = tf5.getText();
        String Salary = tf4.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name should not be Empty");
        } else if (Joiningdate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Date of birth should not be Empty");
        } else if (!Salary.matches(Pattern)) {
            JOptionPane.showMessageDialog(null, "Salary is invalid");
        } else if (Salary.isEmpty()) {
            JOptionPane.showMessageDialog(null, "address should not be Empty");
        } else if (IdNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID Card No should not be Empty");
        } else if (!IdNo.matches(Pattern)) {
            JOptionPane.showMessageDialog(null, "ID Card No. is invalid");
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username should not be Empty");
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password should not be Empty");
        } else if (checkusername == 1) {
            JOptionPane.showMessageDialog(null, "Username already exists");
        } else if (MobileNum.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mobile Number should not be Empty");
        } else if (!MobileNum.matches(Pattern)) {
            JOptionPane.showMessageDialog(null, "Mobile Number is invalid");
        } else {
            try {
                Connection con = Connect();
                SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Convert the Java date to MySQL date format
                String mysqlDate = (date != null) ? mysqlDateFormat.format(date) : "";

                PreparedStatement preparedStatement = con.prepareStatement("insert into users (user_role, name, Joiningdate, mobile_number, IdCardNo, username, password, Salary) values (?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, user_role);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, mysqlDate);
                preparedStatement.setString(4, MobileNum);
                preparedStatement.setString(5, IdNo);
                preparedStatement.setString(6, username);
                preparedStatement.setString(7, password);
                preparedStatement.setString(8, Salary);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "User Entered Successfully");
                frame.setVisible(false);
                new Add_user();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void main(String[] args) {
        new Add_user();
    }
}
