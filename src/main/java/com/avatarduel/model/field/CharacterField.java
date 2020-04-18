package com.avatarduel.model.field;

import java.util.LinkedList;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.PowerUp;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Aura;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one character field
 */
public class CharacterField extends Field {
    private Stance currentStance;
    private LinkedList<FieldPos> skillsPos;
    private boolean firstSummon;
    private boolean hasAttacked;
    private boolean hasPowerUp;

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
     * Return true if this field has power up skill
     * 
     * @return {@code true} if this field has power up skill
     */
    public boolean getHasPowerUp() {
        return hasPowerUp;
    }

    /**
     * @param firstSummon the firstSummon to set
     */
    public void setFirstSummon(boolean firstSummon) {
        this.firstSummon = firstSummon;
    }

    /**
     * Return true if the card have just been summoned
     * 
     * @return {@code true} if this card have just been summoned
     */
    public boolean getFirstSummon() {
        return firstSummon;
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
        this.firstSummon = true;
    }

    @Override
    public Summonedable removeCard() {
        if (this.card == null)
            return null;
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
        int res;
        hasPowerUp = false;
        if (this.currentStance == Stance.ATTACK) {
            res = ((Character) this.card).getAttack();
            for (FieldPos skillPos : skillsPos) {
                Skill card = (Skill) this.globalField.getCardAtField(Type.SKILL, skillPos);
                if (card instanceof Aura) {
                    res += ((Aura) card).getAttack();
                } else if (card instanceof PowerUp) {
                    hasPowerUp = true;
                }
            }
        } else {
            res = ((Character) this.card).getDefense();
            for (FieldPos skillPos : skillsPos) {
                Skill card = (Skill) this.globalField.getCardAtField(Type.SKILL, skillPos);
                if (card instanceof Aura) {
                    res += ((Aura) card).getDefense();
                } else if (card instanceof PowerUp) {
                    hasPowerUp = true;
                }
            }
        }
        return res;
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
        return card + " (" + currentStance + ", " + hasAttacked + ") " + skillsPos;
    }

}