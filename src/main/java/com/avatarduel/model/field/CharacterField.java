package com.avatarduel.model.field;

import java.util.LinkedList;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one character field
 */
public class CharacterField extends Field {
    private Stance currentStance;
    private LinkedList<FieldPos> skillsPos;

    public CharacterField(int player, int column) {
        super(player, column);
        this.currentStance = Stance.ATTACK;
        this.skillsPos = new LinkedList<>();
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

    @Override
    public Summonedable removeCard() {
        if (this.card == null) return null;
        Summonedable card = this.card;
        this.card = null;
        for (FieldPos skillPos : skillsPos) {
            globalField.removeCardAtField(Type.SKILL, skillPos);
        }
        skillsPos.clear();
        return card;
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
     * Save posision attaching skill to this character on field
     * 
     * @param skillPos skill position will be attached
     */
    public void attachSkill(FieldPos skillPos) {
        skillsPos.add(skillPos);
    }

    /**
     * Remove skill
     * 
     * @param skillPos skill's position
     */
    public void removeSkill(FieldPos skillPos) {
        skillsPos.remove(skillPos);
    }

    @Override
    public String toString() {
        return card + " (" + currentStance + ") " + skillsPos;
    }

}