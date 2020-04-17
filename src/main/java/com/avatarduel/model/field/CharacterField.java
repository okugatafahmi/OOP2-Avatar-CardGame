package com.avatarduel.model.field;

import java.util.LinkedList;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one character field
 */
public class CharacterField extends Field {
    private Stance currentStance;
    private LinkedList<FieldPos> skillsPos;
    private boolean hasAttacked;

    public CharacterField(int player, int column) {
        super(player, column);
        this.currentStance = Stance.ATTACK;
        this.skillsPos = new LinkedList<>();
    }

    /**
     * @param hasAttacked the hasAttacked to set
     */
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Return true if this field has attacked
     * 
     * @return {@code true} if this field has attacked
     */
    public boolean getHasAttacked() {
        return hasAttacked;
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
            int res = ((Character) this.card).getAttack();
            for (FieldPos skillPos : skillsPos) {
                res += ((Skill) this.globalField.getCardAtField(Type.SKILL, skillPos)).getAttack();
            }
            return res;
        } else {
            int res = ((Character) this.card).getDefense();
            for (FieldPos skillPos : skillsPos) {
                res += ((Skill) this.globalField.getCardAtField(Type.SKILL, skillPos)).getDefense();
            }
            return res;
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

    /**
     * Get the total attack value of this field
     * 
     * @return total attack value
     */
    public int getTotalAttack() {
        int res = ((Character) this.card).getAttack();
        for (FieldPos skillPos : skillsPos) {
            res += ((Skill) this.globalField.getCardAtField(Type.SKILL, skillPos)).getAttack();
        }
        return res;
    }

    @Override
    public String toString() {
        return card + " (" + currentStance + ", " + hasAttacked + ") " + skillsPos;
    }

}