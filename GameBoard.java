package org.cis120.minesweeper;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Minesweeper m; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 500;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        m = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    Point p = e.getPoint();
                    // updates the model given the coordinates of the mouseclick
                    if (isLeftMouseButton(e)) {
                        m.playTurn(p.y * m.getSideSize() / BOARD_WIDTH,
                                p.x * m.getSideSize() / BOARD_WIDTH);
                    }
                    if (isRightMouseButton(e)) {
                        m.playFlag(p.y * m.getSideSize() / BOARD_WIDTH,
                                p.x * m.getSideSize() / BOARD_WIDTH);
                    }
                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }
            });
    }



    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        m.reset();
        repaint();
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Undoes the last move.
     */
    public void undo() {
        m.undo();
        updateStatus();
        repaint();

        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText(Integer.toString(m.numBombsLeft()) + " Bombs Left");
        boolean winner = m.checkWinner();
        boolean loser = m.checkLoser();
        if (loser) {
            status.setText("You lose!!!");
        }
        if (winner) {
            status.setText("You win!!!");
        }
    }

    public void flipAllTiles(Graphics g) {

        int boxSize = BOARD_WIDTH / m.getSideSize();
        for (int i = 0; i < m.getSideSize(); i++) {
            for (int j = 0; j < m.getSideSize(); j++) {
                g.drawLine(boxSize * i, 0, boxSize * i, BOARD_WIDTH);
                g.drawLine(0, boxSize * j, BOARD_WIDTH, boxSize * j);

            }
        }

        for (int i = 0; i < m.getSideSize(); i++) {
            for (int j = 0; j < m.getSideSize(); j++) {
                int state = m.getCell(i, j);
                if (state != 10 && m.getTile(i, j).getIsFlipped()) {
                    g.drawString(Integer.toString(state), 25 + boxSize * i, 25 + boxSize * j);
                } else if (state == 10 && m.getTile(i, j).getIsFlipped()) {
                    g.drawOval(15 + boxSize * i, 15 + boxSize * j, 20, 20);
                }
            }
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int boxSize = BOARD_WIDTH / m.getSideSize();
        // Draws board grid
        for (int i = 0; i < m.getSideSize(); i++) {
            for (int j = 0; j < m.getSideSize(); j++) {
                g.drawLine(boxSize * i, 0, boxSize * i, BOARD_WIDTH);
                g.drawLine(0, boxSize * j, BOARD_WIDTH, boxSize * j);

            }
        }

        for (int i = 0; i < m.getSideSize(); i++) {
            for (int j = 0; j < m.getSideSize(); j++) {
                int state = m.getCell(i, j);
                if (m.getTile(i, j).getIsFlagged()) {
                    g.drawString("|>", 25 + boxSize * i, 25 + boxSize * j);
                }
                if (state != 10 && m.getTile(i, j).getIsFlipped()) {
                    g.drawString(Integer.toString(state), 25 + boxSize * i, 25 + boxSize * j);
                } else if (state == 10 && m.getTile(i, j).getIsFlipped()) {
                    g.drawOval(15 + boxSize * i, 15 + boxSize * j, 20, 20);
                    updateStatus();
                    flipAllTiles(g);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_WIDTH);
    }
}
