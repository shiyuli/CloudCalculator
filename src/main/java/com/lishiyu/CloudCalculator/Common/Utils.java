package com.lishiyu.CloudCalculator.Common;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static void debug(String message) {
        System.out.println(message);
    }

    public static void showMessageBox(String message, String title, Container parent) {
        if(title == null) {
            JOptionPane.showMessageDialog(parent, message);
        } else {
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
        }
    }
}
