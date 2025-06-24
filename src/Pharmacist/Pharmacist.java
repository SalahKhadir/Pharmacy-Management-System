package Pharmacist;
import Login.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pharmacist{
    private JFrame frame;
    JSeparator separate;
    private String username;

    public Pharmacist(String user){
        this.username=user;
        GUI();
    }
    private void GUI(){

        frame =new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel label = new JLabel("Pharmacy", SwingConstants.CENTER);
        label.setBounds(0, 25, 1300, 50);
        label.setFont(new Font("Verdana", Font.BOLD, 32));
        label.setForeground(new Color(34, 139, 34)); // Forest Green

        separate = new JSeparator();
        separate.setBounds(0, 70, 1370, 10);


        String imagePath = "src/Admin/images/AddMedicine.jpg";
        ImageIcon icon = new ImageIcon(imagePath);
        JButton add_Medicine = new JButton("Add Medicine", icon);
        add_Medicine.setBackground(Color.decode("#228B22"));
        add_Medicine.setFont(new Font("Arial", Font.BOLD, 20));
        add_Medicine.setForeground(Color.WHITE);
        add_Medicine.setBounds(300, 120, 300, 100);

        String cimagePath = "src/Admin/images/Sell.jpg";
        ImageIcon cicon = new ImageIcon(cimagePath);
        JButton cell_Medicine = new JButton("Sell Medicine", cicon);
        cell_Medicine.setBackground(Color.decode("#228B22"));
        cell_Medicine.setFont(new Font("Arial", Font.BOLD, 20));
        cell_Medicine.setForeground(Color.WHITE);
        cell_Medicine.setBounds(700,120,300,100);

        String vimagePath = "src/Admin/images/view Medicine.jpg";
        ImageIcon vicon = new ImageIcon(vimagePath);
        JButton view = new JButton("View Medicine", vicon);
        view.setBackground(Color.decode("#228B22"));
        view.setFont(new Font("Arial", Font.BOLD, 20));
        view.setForeground(Color.WHITE);
        view.setBounds(300,250,300,100);

        String bimagePath = "src/Admin/images/bills.jpg";
        ImageIcon bicon = new ImageIcon(bimagePath);
        JButton bill = new JButton("View Bills", bicon);
        bill.setBackground(Color.decode("#228B22"));
        bill.setFont(new Font("Arial", Font.BOLD, 20));
        bill.setForeground(Color.WHITE);
        bill.setBounds(700,250,300,100);

        String uimagePath = "src/Admin/images/update.png";
        ImageIcon uicon = new ImageIcon(uimagePath);
        JButton update=new JButton("Update Medicine ",uicon);
        update.setBackground(Color.decode("#228B22"));
        update.setFont(new Font("Arial", Font.BOLD, 20));
        update.setForeground(Color.WHITE);
        update.setBounds(300,380,300,100);

        String pimagePath = "src/Admin/images/profile.jpg";
        ImageIcon picon = new ImageIcon(pimagePath);
        JButton profile=new JButton("Profile",picon);
        profile.setBackground(Color.decode("#228B22"));
        profile.setFont(new Font("Arial", Font.BOLD, 20));
        profile.setForeground(Color.WHITE);
        profile.setBounds(700,380,300,100);

        String limagePath = "src/Admin/images/logout.png";
        ImageIcon licon = new ImageIcon(limagePath);
        JButton logout = new JButton("Logout", licon);
        logout.setBackground(Color.decode("#228B22"));
        logout.setFont(new Font("Arial", Font.BOLD, 20));
        logout.setForeground(Color.WHITE);
        logout.setBounds(300,540,300,100);

        String eimagePath = "src/Admin/images/exit.png";
        ImageIcon eicon = new ImageIcon(eimagePath);
        JButton exit = new JButton("Exit", eicon);
        exit.setBackground(Color.decode("#228B22"));
        exit.setFont(new Font("Arial", Font.BOLD, 20));
        exit.setForeground(Color.WHITE);
        exit.setBounds(700,540,300,100);

        JPanel panel = new JPanel(null);


        panel.add(add_Medicine);
        panel.add(separate);
        panel.add(cell_Medicine);
        panel.add(view);
        panel.add(bill);
        panel.add(update);
        panel.add(profile);
        panel.add(logout);
        panel.add(exit);

        panel.setBackground(Color.decode("#F0FFF0"));

        frame.add(label,BorderLayout.CENTER);
        frame.add(panel, BorderLayout.CENTER);

        add_Medicine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Add_Medicine();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new update_medicine();
            }
        });
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new View_Medicine();
            }
        });
        bill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new bill();
            }
        });
        cell_Medicine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sell_Medicine(username);
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null,"Do you want to Logout ","Select",JOptionPane.YES_NO_OPTION);
                if(a==0){frame.setVisible(false);
                    new User();
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null,"Do you want to Exit ","Select",JOptionPane.YES_NO_OPTION);
                if(a==0){
                    System.exit(0);
                }
            }
        });
        profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new profile(username);
            }
        });
        frame.setSize(1368,766);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new Pharmacist("sa");
    }
}
