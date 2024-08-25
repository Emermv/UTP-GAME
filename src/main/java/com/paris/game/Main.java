package com.paris.game;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int boardWidth = 1920/2;
        int boardHeight = 1080/2;

        JFrame frame = new JFrame("UTP GAME");
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UtpGame utpGame = new UtpGame();
        frame.add(utpGame);
        frame.pack();
        utpGame.requestFocus();
        frame.setVisible(true);
    }
}