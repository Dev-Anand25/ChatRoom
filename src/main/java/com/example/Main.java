package com.example;
import com.example.gui.Join;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.lang.System;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();


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