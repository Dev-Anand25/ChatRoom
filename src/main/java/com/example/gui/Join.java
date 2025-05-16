package com.example.gui;

import javax.swing.*;
import java.awt.event.*;
import java.lang.String;
import java.sql.*;

public class Join extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK, buttonCancel;
    private JTextField txtName, roomName;
    private JLabel tryAgainLabel;
    private JPasswordField txtPass;
    private JButton createRoomButton;
    private Connection conn;

    public Join() {
        setContentPane(contentPane);
        setTitle("Join");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CreateRoom createRoom = new CreateRoom();
                createRoom.setVisible(true);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        databaseconn();

    }

    public void databaseconn(){

        //Database connection which room names and password are stored

        String URL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgrepass";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, username, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            String createTableQuery = "CREATE TABLE IF NOT EXISTS ChatRoom (" +
                    "id SERIAL PRIMARY KEY, " +
                    "RoomName VARCHAR(50) NOT NULL, " +
                    "Password VARCHAR(50) NOT NULL" +
                    ");";

            Statement stmt = conn.createStatement();
            stmt.execute(createTableQuery);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void onOK() {

        String user = txtName.getText();
        String rname = roomName.getText();
        String pass = String.valueOf(txtPass.getPassword());

        if (rname.isEmpty() || pass.isEmpty()) {
            tryAgainLabel.setText("All fields are required.");
            return;
        }

        String query = "SELECT * FROM chatroom WHERE RoomName = ? AND Password = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, rname);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ChatRoom chatRoom = new ChatRoom(user, rname);
                chatRoom.setVisible(true);
                dispose();
            } else {
                tryAgainLabel.setText("Try Again. Incorrect Password");
                txtName.setText("");
                roomName.setText("");
                txtPass.setText("");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void onCancel() {
        dispose();
    }

}
