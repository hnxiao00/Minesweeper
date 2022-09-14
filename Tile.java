package org.cis120.minesweeper;


/* This class has the fields and methods for the tiles that will be used
* as the tiles in minesweeper. Each tile keeps track of if it is flipped
* or flagged as well as the value stored in the tile. It can return/set
* values, flip/return the flip status, flag/return the flag status of
* each tile and return a copy of a tile.
* */
public class Tile {

    private boolean isFlipped;
    private boolean isFlagged;
    private int value;
    private boolean isBomb;

    //When each tile is instantiated, it is unflipped and unflagged
    public Tile() {
        isFlipped = false;
        isFlagged = false;
        value = 0;
        isBomb = false;
    }

    /**
     * setValue is a setter that takes in the value to give the tile
     *
     * @param val the value to give the tile
     */    public void setValue(int val) {
        this.value = val;
    }

    /**
     * getValue is a getter for the value of a tile
     *
     * @return the value of the tile
     */    public int getValue() {
        return value;
    }

    /**
     * getIsBomb checks the value of the tile and if it is 10,
     * it makes getIsBomb true, any other value makes getIsBomb false.
     *
     * @return true if the tile contains a bomb, false otherwise
     */
    public boolean getIsBomb() {
        if (value == 10) {
            isBomb = true;
        } else {
            isBomb = false;
        }
        return isBomb;
    }


    /**
     * getIsFlipped is a getter for the flipped status of the tile
     *
     * @return if the tile is flipped
     */
    public boolean getIsFlipped() {
        return isFlipped;
    }

    /**
     * setIsFlipped is a setter that sets the flipped status of a tile
     *
     * @param t true if the tile needs to be flipped, false if it needs to be not flipped
     * @return True if isFlipped is true, false if isFlipped is false
     */
    public boolean setIsFlipped(boolean t) {
        isFlipped = t;
        return isFlipped;
    }

    /**
     * flip makes isFlipped true
     *
     */
    public void flip() {
        isFlipped = true;
    }

    /**
     * getIsFlagged returns the flag status of a tile
     *
     * @return the flag status of the tile
     */
    public boolean getIsFlagged() {
        return isFlagged;
    }

    /**
     * setIsFlagged sets the given tile's flag status
     *
     * @param t a boolean of what to change isflagged to
     * @return true if the tile is flagged, false if the tile is not
     */
    public boolean setIsFlagged(boolean t) {
        isFlagged = t;
        return isFlagged;
    }

    /**
     * flag changes the flagged status of the tile
     */
    public void flag() {
        if (!isFlipped) {
            isFlagged = !isFlagged;
        }
    }

    /**
     * copyTile copies the tile and returns a copy
     *
     * @return the Tile at the row and column specified on the board
     */

    public Tile copyTile() {
        Tile newTile = new Tile();
        newTile.setValue(this.value);
        newTile.setIsFlagged(this.isFlagged);
        newTile.setIsFlipped(this.isFlipped);
        return newTile;
    }
}


