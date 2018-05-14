package com.exercise5.RingBased;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends Frame implements ActionListener {

    public static volatile String command = "{\"start\":\"0\", \"time\":\"1000\", \"port\":\"9001\"}";
    TextField commandField;
    Button b1;

    public UI() {
        Font font1 = new Font("SansSerif", Font.BOLD, 26);
        commandField = new TextField();
        commandField.setFont(font1);
        commandField.setBounds(50, 50, 1400, 300);
        commandField.setText(command);
        commandField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    command = commandField.getText();
                    Util util = new Util();
                    String order = UI.command;
                    int clientPort = Integer.parseInt(util.getJSON(order, "port"));
                    util.TCPSend("localhost", clientPort, order);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        b1 = new Button("Send command");
        b1.setFont(font1);
        b1.setBounds(50,400,500,200);
        b1.addActionListener(this);
        add(commandField);
        add(b1);
        setSize(1600, 700);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        command = commandField.getText();
    }
}