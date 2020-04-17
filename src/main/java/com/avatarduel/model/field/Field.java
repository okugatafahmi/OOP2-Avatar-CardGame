package com.avatarduel.model.field;

import com.avatarduel.gameplay.GlobalField;
import com.avatarduel.model.card.Summonedable;

/**
 * Class for the field
 */
public abstract class Field {
    protected Summonedable card;
    protected GlobalField globalField;
    protected FieldPos fieldPos;

    public Field(int player, int column) {
        this.fieldPos = new FieldPos(player, column);
    }

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
    public abstract Summonedable removeCard();

    /**
     * @param globalField the globalField to set
     */
    public void setGlobalField(GlobalField globalField) {
        this.globalField = globalField;
    }

    public enum Type {
        CHARACTER, SKILL
    }
}