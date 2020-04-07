
package com.avatarduel.gui;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu player
 */
import com.avatarduel.model.card.*;


public class Field {
    private Card card;

    
    public Field() {
        this.card = null;
    }
    
    public Field(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }
}