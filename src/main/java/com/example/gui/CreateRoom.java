package com.example.gui;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CreateRoom extends JDialog implements ActionListener {
    private JPanel contentPane;
    private JButton btncreate;
    private JButton buttonCancel;
    private JTextField RoomName;
    private JPasswordField rpass;
    private JPasswordField passwordField2;
    private JLabel tryAgainLabel;

    public CreateRoom() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Create Room");
        setSize(400, 200);
        setLocationRelativeTo(null);

        btncreate.addActionListener(this);
        buttonCancel.addActionListener(this);
        setVisible(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btncreate) {
            createroom();
        } else if (e.getSource() == buttonCancel) {
            onCancel();
        }
    }

    private void createroom() {
        String rname = RoomName.getText().trim();
        String pass = String.valueOf(rpass.getPassword());
        String pass2 = String.valueOf(passwordField2.getPassword());

        if (rname.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
            tryAgainLabel.setText("All fields are required.");
            return;
        }

        if (!pass.equals(pass2)) {
            tryAgainLabel.setText("Passwords do not match.");
            rpass.setText("");
            passwordField2.setText("");
            return;
        }

        try {
            String URL = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "postgrepass";

            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, username, password);

            // Ensure table exists before inserting
            String createTableQuery = "CREATE TABLE IF NOT EXISTS chatroom (" +
                    "id SERIAL PRIMARY KEY, " +
                    "RoomName VARCHAR(50) NOT NULL, " +
                    "Password VARCHAR(50) NOT NULL)";
            Statement createStmt = conn.createStatement();
            createStmt.execute(createTableQuery);

            // Insert into chatroom table
            String insertQuery = "INSERT INTO chatroom(RoomName, Password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, rname);
            stmt.setString(2, pass);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Room Created Successfully");

            dispose(); // Close this dialog
            new Join().setVisible(true); // Open Join GUI

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating room: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int onCancel() {
        dispose();
        return 0;
    }
}
