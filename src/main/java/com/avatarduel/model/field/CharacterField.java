package com.avatarduel.model.field;

import java.util.LinkedList;

import com.avatarduel.model.card.Character;

/**
 * Class that responsible for one character field
 */
public class CharacterField extends Field {
    private Stance currentStance;
    private LinkedList<SkillAttached> skills;

    public CharacterField() {
        this.currentStance = Stance.ATTACK;
        this.skills = new LinkedList<>();
    }

    /**
     * Set card
     * 
     * @param card card to set
     * @throws CardInFieldExist if this field has had card
     */
    public void setCard(Character card) throws CardInFieldExist {
        if (this.card != null) {
            throw new CardInFieldExist();
        }
        this.card = card;
    }

    /**
     * Change character stance
     */
    public void changeStance() {
        if (this.currentStance == Stance.ATTACK) {
            this.currentStance = Stance.DEFEND;
        } else {
            this.currentStance = Stance.ATTACK;
        }
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

    /**
     * Attach skill to this character on field
     * 
     * @param skill skill will be attached
     */
    public void attachSkill(SkillAttached skill) {
        skills.add(skill);
    }

    @Override
    public String toString() {
        return card + " (" + currentStance + ") " + skills;
    }

}