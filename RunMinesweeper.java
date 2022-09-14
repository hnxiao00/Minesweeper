package org.cis120.minesweeper;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunMinesweeper implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(300, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Game Ready");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        final JFrame instructions = new JFrame("MineSweeper Instructions");
        instructions.setLocation(300, 100);
        final JTextArea textArea = new JTextArea("Welcome to Minesweeper! " +
                "Your goal is to uncover all nonbomb tiles to win, while avoiding clicking bombs. "
                + "There are 10 bombs in this 10 x 10 grid."
                 + " Left click on a tile to uncover its value, " + "" +
                "which tells you how many bombs are in adjacent tile. " +
                "If you suspect a tile contains a bomb, right click on it to place a flag. " +
                "This makes the Bombs Left count go down, but you don't have to flag bombs to win."
                + " At any point in time, you can click the reset button to reset the game board. "
                + "You can also hit the undo button if you would like to undo your previous moves."

                , 20, 30);

        textArea.setEditable(false); //user is not allowed to change text
        textArea.setLineWrap(true); //wrap text
        textArea.setWrapStyleWord(true); //if the word is too long to fit, move it to the next line
        instructions.add(textArea);
        instructions.pack();
        instructions.setVisible(true);

        // Start the game
        board.reset();
    }
}