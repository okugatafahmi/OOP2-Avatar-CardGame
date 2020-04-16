package com.avatarduel.model.field;

import com.avatarduel.model.card.Card;

/**
 * Class that responsible for one skill field
 */
public class SkillField {
    protected Card card;

    /**
     * Set card on field
     * 
     * @param card card to be set
     * @throws CardInFieldExist if there has been card on field
     */
    public void setCard(Card card) throws CardInFieldExist {
        if (this.card != null) {
            throw new CardInFieldExist();
        }
        this.card = card;
    }

    /**
     * Get the card on field
     * 
     * @return the card on field. {@code null} if there is no card
     */
    public Card getCard() {
        return this.card;
    }
}