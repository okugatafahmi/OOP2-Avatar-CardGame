package com.avatarduel.model.field;

import com.avatarduel.model.card.Skill;

/**
 * Class to save information of skill card and its position
 */
public class SkillAttached {
    private Skill skill;
    private CardPos cardPos;

    /**
     * Constructor
     * 
     * @param skill skill card
     * @param cardPos skill card position
     */
    public SkillAttached(Skill skill, CardPos cardPos) {
        this.skill = skill;
        this.cardPos = cardPos;
    }

    /**
     * @return the skill
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     * @return the cardPos
     */
    public CardPos getCardPos() {
        return cardPos;
    }

    @Override
    public String toString() {
        return skill + " " + cardPos;
    }
}