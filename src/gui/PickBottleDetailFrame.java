package gui;

import javax.swing.*;
import java.util.ArrayList;

public class PickBottleDetailFrame extends JFrame {
    JLabel titleLabel=new JLabel("Bottle title:");
    JLabel titleField=new JLabel();

    JLabel messageLabel=new JLabel("Bottle message:");
    JTextArea messageArea=new JTextArea();

    // 评论展示面板，可滚动
    JPanel commentPanel=new JPanel();
    ArrayList<JLabel> commentLabelList=new ArrayList<>();
    JScrollPane commentScrollPane=new JScrollPane(commentPanel);

    // 评论组件
    JTextField commentField=new JTextField();
    JButton commentButton=new JButton("Comment");
    public PickBottleDetailFrame(){
        super("Pick Bottle Detail");
        setSize(240, 250);
        setLocation(200,200);

        JPanel panel=new JPanel();
        panel.setLayout(null);

    }

}
