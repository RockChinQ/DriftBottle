package gui;

import core.Main;
import data.models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DashboardMain extends JFrame {

    JLabel usernameLabel=new JLabel("Username:");
    JTextField usernameField=new JTextField();

    JButton dropBottle=new JButton("Drop Bottle");
    JButton pickBottle=new JButton("Pick Bottle");

    DropBottleDetailFrame dropBottleDetailFrame=new DropBottleDetailFrame();
    PickBottleDetailFrame pickBottleDetailFrame=new PickBottleDetailFrame();
    public DashboardMain(User user){
        super("Dashboard");
//        System.out.println("ui"+user.username);
        setSize(240, 260);
        setLocation(200,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel=new JPanel();
        panel.setLayout(null);

        usernameLabel.setBounds(10,10,200,20);
        usernameField.setBounds(10,30,200,20);

        usernameField.setText(user.username);

        panel.add(usernameLabel);
        panel.add(usernameField);

        dropBottle.setBounds(10,60,200,70);
        dropBottle.addActionListener(e -> {
            dropBottleDetailFrame.setVisible(true);
        });
        pickBottle.setBounds(10,140,200,70);
        pickBottle.addActionListener(e -> {
            try {
                pickBottleDetailFrame.randomlyPickOne();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Pick bottle failed: "+ex.getMessage());
            }
        });

        panel.add(dropBottle);
        panel.add(pickBottle);
        add(panel);

        this.repaint();
    }
}
