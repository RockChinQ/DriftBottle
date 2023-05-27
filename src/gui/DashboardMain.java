package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardMain extends JFrame {
    JButton dropBottle=new JButton("Drop Bottle");
    JButton pickBottle=new JButton("Pick Bottle");

    DropBottleDetailFrame dropBottleDetailFrame=new DropBottleDetailFrame();
    PickBottleDetailFrame pickBottleDetailFrame=new PickBottleDetailFrame();
    public DashboardMain(){
        super("Dashboard");
        setSize(240, 250);
        setLocation(200,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel=new JPanel();
        panel.setLayout(null);

        dropBottle.setBounds(10,10,200,70);
        dropBottle.addActionListener(e -> {
            dropBottleDetailFrame.setVisible(true);
        });
        pickBottle.setBounds(10,90,200,70);
        pickBottle.addActionListener(e -> {
            pickBottleDetailFrame.setVisible(true);
        });

        panel.add(dropBottle);
        panel.add(pickBottle);
        add(panel);
    }
}
