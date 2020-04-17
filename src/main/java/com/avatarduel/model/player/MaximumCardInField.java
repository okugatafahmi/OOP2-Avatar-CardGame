package com.avatarduel.model.player;

public class MaximumCardInField extends Exception {
    public MaximumCardInField(String type, int maximum) {
        super("Can't summon " + type + " card because it has reached maximum (" + Integer.toString(maximum)
                + " cards)");
    }

    /**
     *
     */
    private static final long serialVersionUID = -1666875356761135046L;
}