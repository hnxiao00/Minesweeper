package org.cis120.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {

    @Test
    //Test to see if there are exactly 10 bombs created from AddBombs
    public void testAddBombs() {
        Minesweeper m = new Minesweeper();
        int bombCounter = 0;
        for (int i = 0; i < m.getSideSize(); i++) {
            for (int j = 0; j < m.getSideSize(); j++) {
                if (m.getTile(i, j).getIsBomb()) {
                    bombCounter += 1;
                }
            }
        }
        assertEquals(10, bombCounter);
    }

    @Test
    //Test to see that adding 1 bomb will result in surrounding tiles having a value of 1
    public void testRecursionOfUncoverZeros() {
        Minesweeper m = new Minesweeper();
        m.createEmptyBoard();
        //add one bomb and check all surrounding tiles have value of 1.
        m.getTile(1, 1).setValue(10);
        for (int i = 0; i < m.getSideSize(); i++) {
            m.getTile(0, i).setValue(10);
        }
        for (int i = 0; i < m.getSideSize(); i++) {
            m.getTile(i, 0).setValue(10);
        }
        for (int i = 0; i < m.getSideSize(); i++) {
            m.getTile(i, 9).setValue(10);
        }
        for (int i = 0; i < m.getSideSize(); i++) {
            m.getTile(9, i).setValue(10);
        }
        int flipCounter = 0;
        m.uncoverZeros(3, 5);
        for (int i = 1; i < m.getSideSize() - 1; i++) {
            for (int j = 1; j < m.getSideSize() - 1; j++) {
                if (m.getTile(i, j).getIsFlipped() && m.getTile(i, j).getValue() < 9) {
                    flipCounter += 1;
                }
            }
        }
        assertEquals(flipCounter, flipCounter);
    }


    @Test
    //Test to see that uncovering all nonbomb tiles will result in a win
    public void testUncoveringAllNonBombsWins() {
        Minesweeper m = new Minesweeper();
        m.createEmptyBoard();
        for (int i = 0; i < 10; i++) {
            m.getTile(0, i).setValue(10);
        }
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                m.getTile(i, j).flip();
            }
        }
        m.addValues();
        int bombs = 0;
        int flipped = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!m.getTile(i, j).getIsBomb() && m.getTile(i, j).getIsFlipped()) {
                    flipped += 1;
                }
                if (m.getTile(i, j).getIsBomb() && !m.getTile(i, j).getIsFlipped()) {
                    bombs += 1;
                }
            }
        }
        assertTrue(m.checkWinner());
        assertEquals(10, bombs);
        assertEquals(90, flipped);
        assertTrue(m.getIsGameOver());
    }

    @Test
    //Test to see that clicking 1 bomb will result in a loss
    public void testClickingBombsLose() {
        Minesweeper m = new Minesweeper();
        m.createEmptyBoard();
        for (int i = 0; i < 10; i++) {
            m.getTile(0, i).setValue(10);
        }
        m.uncoverZeros(0, 1);
        assertTrue(m.checkLoser());
        assertTrue(m.getIsGameOver());
    }

    @Test
    /*Test to see that making a valid move will increase the size of the game
    state by 1, and that undoing a move will decrease the size of the game state by 1. */
    public void testChangeGameState() {
        Minesweeper m = new Minesweeper();
        m.createEmptyBoard();
        m.getTile(9, 8).setValue(10);
        m.getTile(8, 9).setValue(10);
        m.addValues();
        m.saveGameState();
        assertEquals(1, m.getGameStateSize());
        m.playTurn(1, 4);
        assertEquals(2, m.getGameStateSize());
        m.playFlag(9, 9);
        assertEquals(3, m.getGameStateSize());
    }

    @Test
    /*Test to see that making a valid move will increase the size of the game
    state by 1, and that undoing a move will decrease the size of the game state by 1. */
    public void testUndo() {
        Minesweeper m = new Minesweeper();
        m.createEmptyBoard();
        m.getTile(9, 8).setValue(10);
        m.getTile(8, 9).setValue(10);
        m.addValues();
        m.saveGameState();
        m.playTurn(1, 4);
        m.playFlag(9, 9);
        m.undo();
        assertEquals(2, m.getGameStateSize());
        m.undo();
        assertEquals(1, m.getGameStateSize());
        //Checking tiles to make sure they have the same state as originally initialized
        assertEquals(10, m.getTile(9, 8).getValue());
        assertEquals(10, m.getTile(8, 9).getValue());
        assertEquals(0, m.getTile(0, 0).getValue());
    }
}
