package com.example;
import com.example.gui.Join;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                Join join = new Join();
                join.setVisible(true);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}