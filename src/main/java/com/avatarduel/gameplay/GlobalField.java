package com.avatarduel.gameplay;

import com.avatarduel.model.card.Summonedable;

public interface GlobalField {

    /**
     * Get card on field
     * 
     * @param player player's field
     * @param type   field type (CHARACTER or SKILL)
     * @param column field's colum
     * @return card on specified player's column's field
     */
    public Summonedable getCardOnField(int player, Summonedable.Type type, int column);

    /**
     * Remove card on field
     * 
     * @param player player's field
     * @param type   field type (CHARACTER or SKILL)
     * @param column field's column
     */
    public void removeCardOnField(int player, Summonedable.Type type, int column);
}