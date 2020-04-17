package com.avatarduel.model.player;

import com.avatarduel.model.field.PlaceCardException;

public class MaximumCardInField extends PlaceCardException {
    public MaximumCardInField(String type, int maximum) {
        super(type + " card has reached maximum (" + Integer.toString(maximum) + " cards)");
    }

    /**
     *
     */
    private static final long serialVersionUID = -1666875356761135046L;
}