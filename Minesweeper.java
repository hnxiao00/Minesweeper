package org.cis120.minesweeper;

import java.util.LinkedList;
/**

 **/
public class Minesweeper {

    private Tile[][] board;
    private boolean gameOver;
    private int sideSize = 10;
    private int numBombs = 10;
    private LinkedList<Tile[][]> gameState = new LinkedList<Tile[][]>();



    /**
     * Constructor sets up game state.
     */
    public Minesweeper() {
        reset();
    }


    /**
     * newTile makes one of the tiles a bomb given a game board. It randomly
     * picks a tile within bounds and if the tile in the board is not a bomb
     * and is not flipped, it changes the tile to be a bomb by setting its
     * value to 10. Otherwise, newTile returns false.
     *
     * @param tile is the game board in which to spawn new bombs
     * @return whether the spawn was successful
     */
    public boolean newTile(Tile[][] tile) {
        //initialize random numbers for position
        int a = (int) Math.round(Math.random() * (sideSize - 1));
        int b = (int) Math.round(Math.random() * (sideSize - 1));
        if (tile[a][b].getIsBomb() || tile[a][b].getIsFlipped()) {
            return false;
        } else {
            tile[a][b].setValue(10);
            tile[a][b].getIsBomb();
            return true;
        }
    }

    /**
     * addBombs adds numBomb number of bombs to the board.
     * Bombs are created using newTile, and if a bomb
     * placement is successful then bombCounter increases
     * by 1. Bombs are added until bombCounter = numBombs,
     * which insures that the board will have exactly numBomb bombs.
     *
     */
    public void addBombs() {
        //add numBombs bombs
        int bombCounter = 0;
        while (bombCounter < numBombs) {
            if (newTile(board)) {
                bombCounter += 1;
            } else {
                bombCounter += 0;
            }
        }
    }

    /**
     * addValue loops through each tile of the board, and
     * for all nonbomb tiles, and adds up the number of
     * bombs in are in the surrounding valid (not out
     * of bounds) tiles.
     *
     */
    public void addValues() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                if (!board[i][j].getIsBomb()) {
                    int counter = 0;
                    if (1 + i < sideSize && board[i + 1][j].getIsBomb()) {
                        counter += 1;
                    }
                    if (1 + j < sideSize && board[i][j + 1].getIsBomb()) {
                        counter += 1;
                    }
                    if (i - 1 >= 0 && board[i - 1][j].getIsBomb()) {
                        counter += 1;
                    }
                    if (j - 1 >= 0 && board[i][j - 1].getIsBomb()) {
                        counter += 1;
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1].getIsBomb()) {
                        counter += 1;
                    }
                    if (i + 1 < sideSize && j - 1 >= 0 && board[i + 1][j - 1].getIsBomb()) {
                        counter += 1;
                    }
                    if (j + 1 < sideSize && i - 1 >= 0 && board[i - 1][j + 1].getIsBomb()) {
                        counter += 1;
                    }
                    if (i + 1 < sideSize && j + 1 < sideSize && board[i + 1][j + 1].getIsBomb()) {
                        counter += 1;
                    }
                    board[i][j].setValue(counter);
                }
            }
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     * It creates an empty game board of new tiles, spawns
     * bombs, and adds values.
     */
    public void reset() {
        createEmptyBoard();
        addBombs();
        addValues();
        saveGameState();
    }


    /**
     * createEmptyBoard makes the game not over and initializes
     * the game board by creating a Tile array filled with new tiles
     */
    public void createEmptyBoard() {
        board = new Tile[sideSize][sideSize];
        gameOver = false;
        gameState = new LinkedList<Tile[][]>();
        //Initialize the empty board
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    /**
     * uncoverZeros flips the tile located at the ath row and bth column. If
     * the value of the flipped tile is equal to 0, it recursively checks
     * every surrounding tile and flips until it reaches a nonzero value tile.
     * If it reaches a flagged tile that must be uncovered, it unflags and covers it.
     *
     * @param a an integer that represents the row number in the game board
     * @param b an integer that represents the column number in the game board
     */
    public void uncoverZeros(int a, int b) {
        board[a][b].flip();
        if (board[a][b].getValue() == 0) {

            if (a + 1 < 10 && a > -1 && b < 10 && b > -1 &&
                    !board[a + 1][b].getIsFlipped()) {
                uncoverZeros(a + 1, b);
            }
            if (a < 10 && a - 1 > -1 && b < 10 && b > -1 &&
                    !board[a - 1][b].getIsFlipped()) {
                uncoverZeros(a - 1, b);
            }
            if (a + 1 < 10 && a > -1 && b + 1 < 10 && b > -1 &&
                    !board[a + 1][b + 1].getIsFlipped()) {
                uncoverZeros(a + 1, b + 1);
            }
            if (a + 1 < 10 && a > -1 && b < 10 && b - 1 > -1 &&
                    !board[a + 1][b - 1].getIsFlipped()) {
                uncoverZeros(a + 1, b - 1);
            }
            if (a < 10 && a - 1 > -1 && b + 1 < 10 && b > -1 &&
                    !board[a - 1][b + 1].getIsFlipped()) {
                uncoverZeros(a - 1, b + 1);
            }
            if (a < 10 && a - 1 > -1 && b < 10 && b - 1 > -1 &&
                    !board[a - 1][b - 1].getIsFlipped()) {
                uncoverZeros(a - 1, b - 1);
            }
            if (a < 10 && a > -1 && b + 1 < 10 && b > -1 &&
                    !board[a][b + 1].getIsFlipped()) {
                uncoverZeros(a, b + 1);
            }
            if (a < 10 && a > -1 && b < 10 && b - 1 > -1  &&
                    !board[a][b - 1].getIsFlipped()) {
                uncoverZeros(a, b - 1);
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getIsFlipped()) {
                    board[i][j].setIsFlagged(false);
                }
            }
        }
    }


    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * flagged or after the game has ended.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int c, int r) {
        if (gameOver) {
            return false;
        }
        if (this.board[r][c].getIsFlagged() || this.board[r][c].getIsFlipped()) {
            return false;
        } else {
            uncoverZeros(r, c);
            saveGameState();
            return true;
        }
    }

    /**
     * playFlag allows players to flag a tile. Returns true if the move is
     * successful and false if a player tries to flag in a location that is
     * flipped or after the game has ended.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the flag was successful
     */
    public boolean playFlag(int c, int r) {
        if (gameOver) {
            return false;
        }
        if (this.board[r][c].getIsFlipped()) {
            return false;
        } else {
            this.board[r][c].flag();
            saveGameState();
            return true;
        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition
     * by uncovering all the nonbomb tiles. If the player has won, it
     * returns true, otherwise false.
     *
     * @return true if the game is won, otherwise false
     */
    public boolean checkWinner() {
        // Check if all nonbomb tiles are flipped and if all bomb tiles are flagged
        int numTilesFlipped = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getIsFlipped()) {
                    numTilesFlipped += 1;
                }
            }
        }
        if (numTilesFlipped == (sideSize * sideSize - numBombs)) {
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkLoser checks whether the game has reached a lose condition
     * by uncovering at least 1 bomb tile. If the player has lost, it
     * returns true, otherwise false.
     *
     * @return true if the game is lost, otherwise false
     */
    public boolean checkLoser() {
        int numBombs = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getIsFlipped() && board[i][j].getIsBomb()) {
                    numBombs += 1;
                }
            }
        }
        if (numBombs > 0) {
            gameOver = true;
            flipAll();
            return true;
        } else {
            return false;
        }
    }

    /**
     * numBombsLeft calculates the number of flagged tiles and
     * subtracts that from the number of bombs initialized to
     * show the number of bombs left the player needs to find.
     * This is the state of the game and is the value returned.
     *
     * @return the number of bombsLeft
     */
    public int numBombsLeft() {
        // Check how many flags are put on the board
        int flagNum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getIsFlagged()) {
                    flagNum += 1;
                }
            }
        }
        return numBombs - flagNum;
    }

    /**
     * getIsGameOver is a getter for gameOver, which says if the game is over or not
     *
     * @return false if gameOver is false, true if gameOver is true
     */
    public boolean getIsGameOver() {
        return gameOver;
    }

    /**
     * setIsGameOver is a setter for gameOver. Because gameOver is
     * initialized as false and the game ends once gameOver is true,
     * this method changes gameOver to be true when called. It also
     * flips all tiles on the board so the player can see where all
     * bombs were located.
     */
    public void flipAll() {
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                board[i][j].flip();
            }
        }
    }

    /**
     * getSideSize is a getter for the side length of the borad
     *
     * @return the side size of the board
     */
    public int getSideSize() {
        return sideSize;
    }


    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getCell(int r, int c) {
        return this.board[r][c].getValue();
    }


    /**
     * getTile is a getter for the tile specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return the Tile at the row and column specified on the board
     */
    public Tile getTile(int r, int c) {
        return this.board[r][c];
    }
    /**
     * saveGameState is a getter for the length of the game state linked list
     *
     */
    public void saveGameState() {
        Tile[][] newArray = new Tile[sideSize][sideSize];
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                newArray[i][j] = new Tile();
                newArray[i][j] = board[i][j].copyTile();
            }
        }
        gameState.addLast(newArray);
    }

    /**
     * getGameStateSize is a getter for the length of the game state
     *
     * @return the size of the game state
     */
    public int getGameStateSize() {
        return gameState.size();
    }

    /**
     * undo is a method that undos the past move made. It copies the tiles from the second
     * to last game state over to the borad, and it deletes the last
     * game state so that it can't be accessed.
     *
     * @return the Tile at the row and column specified on the board
     */
    public void undo() {
        if (gameState.size() > 1) {
            Tile[][] newArray = gameState.get(gameState.size() - 2);
            gameState.pollLast();
            board = newArray;
            gameOver = false;
        }
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
    }
}
