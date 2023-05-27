package gui;

import core.Main;

import javax.swing.*;
import java.sql.SQLException;

public class DropBottleDetailFrame extends JFrame {

    JLabel titleLabel=new JLabel("Type your title:");
    JTextField titleField=new JTextField();

    JLabel messageLabel=new JLabel("Type your message:");
    JTextArea messageArea=new JTextArea();

    JButton dropButton=new JButton("Drop");
    public DropBottleDetailFrame(){
        super("Drop Bottle Detail");
        setSize(240, 250);
        setLocation(200,200);

        JPanel panel=new JPanel();
        panel.setLayout(null);

        titleLabel.setBounds(10,10,200,20);
        titleField.setBounds(10,30,200,20);

        messageLabel.setBounds(10,50,200,20);
        messageArea.setBounds(10,70,200,100);

        dropButton.setBounds(10,180,200,20);
        dropButton.addActionListener(e -> {
            if(titleField.getText().equals("")||messageArea.getText().equals("")){
                javax.swing.JOptionPane.showMessageDialog(null, "Title or message cannot be empty!");
                return;
            }
            try {
                Main.dbManager.dropBottle(titleField.getText(),messageArea.getText());
                javax.swing.JOptionPane.showMessageDialog(null, "Drop bottle successfully!");
                titleField.setText("");
                messageArea.setText("");
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Drop bottle failed: "+ex.getMessage());
            }
        });

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(messageLabel);
        panel.add(messageArea);
        panel.add(dropButton);
        add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
