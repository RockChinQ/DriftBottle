package gui;

import core.Main;
import data.models.Bottle;
import data.models.Comment;
import utils.TimeUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PickBottleDetailFrame extends JFrame {
    JLabel authorLabel=new JLabel("Author:");
    JLabel authorField=new JLabel();

    JLabel titleLabel=new JLabel("Bottle title:");
    JLabel titleField=new JLabel();

    JLabel messageLabel=new JLabel("Bottle message:");
    JTextArea messageArea=new JTextArea();

    // 评论展示面板，可滚动
    JLabel commentLabel=new JLabel("Comments:");
    JTextArea commentPanel=new JTextArea();
    JScrollPane commentScrollPane=new JScrollPane(commentPanel);

    // 评论组件
    JTextField commentField=new JTextField();
    JButton commentButton=new JButton("Comment");

    Bottle currentBottle = null;
    public PickBottleDetailFrame(){
        super("Pick Bottle Detail");
        setSize(240, 450);
        setLocation(200,200);

        JPanel panel=new JPanel();
        panel.setLayout(null);

        authorLabel.setBounds(10,10,200,20);
        authorField.setBounds(10,30,200,20);

        panel.add(authorLabel);
        panel.add(authorField);

        titleLabel.setBounds(10,50,200,20);
        titleField.setBounds(10,70,200,20);

        messageLabel.setBounds(10,90,200,20);
        messageArea.setBounds(10,110,200,100);
        messageArea.setEditable(false);

        panel.add(titleLabel);
        panel.add(titleField);

        panel.add(messageLabel);
        panel.add(messageArea);

        // 评论展示面板
        commentLabel.setBounds(10,220,200,20);
        commentScrollPane.setBounds(10,240,200,100);
        commentPanel.setEditable(false);
        commentPanel.setLineWrap(true);
        panel.add(commentLabel);
        panel.add(commentScrollPane);

        // 评论组件
        commentField.setBounds(10,350,200,25);
        commentButton.setBounds(10,380,200,20);
        commentButton.addActionListener(e->{
            if(commentField.getText().trim().equals("")){
                javax.swing.JOptionPane.showMessageDialog(null, "Comment cannot be empty!");
                return;
            }

            try{
                Main.dbManager.addCommentForBottle(this.currentBottle, commentField.getText());
//                javax.swing.JOptionPane.showMessageDialog(null, "Comment successfully!");
                commentField.setText("");
                this.updateUIForBottle(this.currentBottle);
            }catch (SQLException ex){
                javax.swing.JOptionPane.showMessageDialog(null, "Comment failed: "+ex.getMessage());
                return;
            }
        });

        panel.add(commentField);
        panel.add(commentButton);

        this.add(panel);
    }

    public void updateUIForBottle(Bottle bottle) throws SQLException {
        this.currentBottle=bottle;
        this.setTitle("#"+bottle.id+" - Pick Bottle Detail");
        this.titleField.setText(bottle.title);
        this.messageArea.setText(bottle.message);

        this.authorField.setText(bottle.user.username);

        // 获取评论
        Comment[] comments=Main.dbManager.getCommentsOfBottle(bottle);
        StringBuilder commentText=new StringBuilder();
        for(Comment comment:comments){
            commentText.append(TimeUtil.millsToMMDDHHmmSS(new Date(comment.created_at).getTime())).append(" ").append("["+comment.user.username+"]").append(" ").append(comment.message).append("\n");
        }
        this.commentPanel.setText(commentText.toString().trim().equals("")?"No comments":commentText.toString());
    }

    public void randomlyPickOne() throws SQLException {
        Bottle bottle=Main.dbManager.randomPick();

        this.updateUIForBottle(bottle);

        this.setVisible(true);
    }
}
