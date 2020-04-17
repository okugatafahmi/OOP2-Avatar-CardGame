package com.avatarduel.model.field;

import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one skill field
 */
public class SkillField extends Field {
    private CardPos attachTo;

    /**
     * Set card on field
     * 
     * @param card     card to be set
     * @param attachTo card position where skill card attach to
     * @throws CardInFieldExist if there has been card on field
     */
    public void setCard(Skill card, CardPos attachTo) throws CardInFieldExist {
        if (this.card != null) {
            throw new CardInFieldExist();
        }
        // TODO apakah perlu mengecek attachTo isi kartu atau tidak??
        this.card = card;
        this.attachTo = attachTo;
    }

    /**
     * Remove card
     * 
     * @return card in this field
     */
    @Override
    public Summonedable removeCard() {
        Summonedable card = this.card;
        this.card = null;
        this.attachTo = null;
        return card;
    }

    /**
     * Get the card on field
     * 
     * @return the card on field. {@code null} if there is no card
     */
    public Summonedable getCard() {
        return this.card;
    }

    /**
     * @return the attachTo
     */
    public CardPos getAttachTo() {
        return attachTo;
    }

    @Override
    public String toString() {
        return card + " " + attachTo;
    }
}