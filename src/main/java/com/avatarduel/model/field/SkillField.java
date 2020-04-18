package com.avatarduel.model.field;

import com.avatarduel.model.card.Destroy;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one skill field
 */
public class SkillField extends Field {
    private FieldPos attachTo;

    public SkillField(int player, int column) {
        super(player, column);
    }

    /**
     * Set card on field
     * 
     * @param card     card to be set
     * @param attachTo card position where skill card attach to
     * @throws CardInFieldExist  if there has been card on field
     * @throws NoneCharacterCard if the selected character field is empty
     */
    public void setCard(Skill card, FieldPos attachTo) throws CardInFieldExist, NoneCharacterCard {
        if (this.card != null) {
            throw new CardInFieldExist();
        }
        if (this.globalField.getCardAtField(Type.CHARACTER, attachTo) == null) {
            throw new NoneCharacterCard();
        }
        if (card instanceof Destroy) {
            this.globalField.removeCardAtField(Type.CHARACTER, attachTo);
        }
        else {
            this.card = card;
            this.attachTo = attachTo;
            this.globalField.attachSkill(fieldPos, attachTo);
        }
    }

    @Override
    public Summonedable removeCard() {
        if (this.card == null) return null;
        Summonedable card = this.card;
        this.card = null;
        // cek apakah character yang dihapus. Jika tidak, hapus skill character
        if (this.globalField.getCardAtField(Type.CHARACTER, attachTo) != null) {
            this.globalField.removeSkillOfCharacterAtField(fieldPos, attachTo);
        }
        this.attachTo = null;
        return card;
    }

    public void removeCardFromCharacter() {
        this.card = null;
        this.attachTo = null;
    }

    /**
     * Get the card on field
     * 
     * @return the card on field. {@code null} if there is no card
     */
    public Summonedable getCard() {
        return this.card;
    }

    //TODO Sepertinya tidak kepakai
    /**
     * @return the attachTo
     */
    public FieldPos getAttachTo() {
        return attachTo;
    }

    @Override
    public String toString() {
        return card + " " + attachTo;
    }
}