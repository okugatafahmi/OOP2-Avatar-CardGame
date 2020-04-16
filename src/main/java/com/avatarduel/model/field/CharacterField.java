package com.avatarduel.model.field;

import com.avatarduel.model.card.Character;

/**
 * Class that responsible for one character field
 */
public class CharacterField extends SkillField {
    private Stance currentStance;

    public CharacterField() {
        this.currentStance = Stance.ATTACK;
    }

    /**
     * @return the currentStance
     */
    public Stance getCurrentStance() {
        return currentStance;
    }

    /**
     * 
     * @return the character card stance value with currentStance (ATTACK or
     *         DEFENSE)
     */
    public int getStanceValue() {
        if (this.currentStance == Stance.ATTACK) {
            return ((Character) this.card).getAttack();
        } else {
            return ((Character) this.card).getDefense();
        }
    }
}