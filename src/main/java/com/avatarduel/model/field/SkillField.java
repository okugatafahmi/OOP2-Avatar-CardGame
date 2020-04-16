package com.avatarduel.model.field;

import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one skill field
 */
public class SkillField {
    protected Summonedable card;

    /**
     * Set card on field
     * 
     * @param card card to be set
     * @throws CardInFieldExist if there has been card on field
     */
    public void setCard(Summonedable card) throws CardInFieldExist {
        if (this.card != null) {
            throw new CardInFieldExist();
        }
        this.card = card;
    }

    /**
     * Remove card
     */
    public void removeCard() {
        this.card = null;
    }

    /**
     * Get the card on field
     * 
     * @return the card on field. {@code null} if there is no card
     */
    public Summonedable getCard() {
        return this.card;
    }
}