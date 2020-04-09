package com.avatarduel.model.field;

import com.avatarduel.model.card.Card;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu
 * player
 */
public class Field {
    protected Card card;

    public Field(){
        this.card = null;
    }

    public void setCard(Card card){
        this.card = card;
    }

    public Card getCard(){
        return this.card;
    }
}