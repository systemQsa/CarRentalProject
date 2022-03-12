package com.myproject.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListenerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("\n\n" +e.getActionCommand());
    }
}
