package com.avatarduel.model.field;


/**
 * Class for knowing card position
 */
public class CardPos {
    private int player;
    private int column;

    /**
     * Constructor 
     * @param player player (whose field)
     * @param column column field
     */
    public CardPos (int player, int column) {
        this.player = player;
        this.column = column;
    }

    /**
     * @return the player
     */
    public int getPlayer() {
        return player;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(player) + ", " + Integer.toString(column) + ")";
    }
}