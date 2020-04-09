package com.avatarduel.model.field;

import com.avatarduel.model.card.Character;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu player
 */
public class CharacterField extends Field {
    private Stance currentStance;

    public CharacterField() {
        this.currentStance = Stance.ATTACK;
    }

    public int getStanceValue() {
        if (this.currentStance == Stance.ATTACK) {
            return ((Character)this.card).getAttack();
        } else {
            return ((Character)this.card).getDefense();
        }
    }
}