package com.example.gui;
import com.example.backend.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatRoom extends JFrame implements ActionListener{

    private JTextArea txtDisplay;
    private JTextField txtMessage;
    private JButton btnSend;
    private JPanel MainPanel;
    private JLabel chatTitle;
    private Client client;

    public ChatRoom(String user, String rname) {
        setContentPane(MainPanel);
        setTitle("ChatRoom");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only closes this window
        setLocationRelativeTo(null);

        btnSend.addActionListener(this);
        chatTitle.setText(rname+" ChatRoom");
        try {
            client = new Client("localhost", 1234, this, user, rname);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSend){Send();};
    }
    void Send(){
        String outMessage = txtMessage.getText();
        txtMessage.setText("");
        client.getMessage(outMessage);
    }
    public void display(String message){
         txtDisplay.append(message+"\n");
    }

}
