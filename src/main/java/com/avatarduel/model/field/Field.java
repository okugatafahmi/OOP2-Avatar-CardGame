package com.avatarduel.model.field;

import com.avatarduel.model.card.Summonedable;

/**
 * Class for the field
 */
public abstract class Field {
    protected Summonedable card;

    /**
     * @return the card
     */
    public Summonedable getCard() {
        return card;
    }

    /**
     * Remove card
     * 
     * @return card in this field
     */
    public Summonedable removeCard() {
        Summonedable card = this.card;
        this.card = null;
        return card;
    }
}