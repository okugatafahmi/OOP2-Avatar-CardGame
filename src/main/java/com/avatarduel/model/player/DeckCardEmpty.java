package com.avatarduel.model.player;

public class DeckCardEmpty extends Exception {
    private int id;

    public DeckCardEmpty(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     */
    private static final long serialVersionUID = -573536698449457400L;
}