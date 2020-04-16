package com.avatarduel.model.field;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Summonedable;

/**
 * Class that responsible for one column field
 */
public class ColumnField {
    private CharacterField characterField;
    private SkillField skillField;

    public ColumnField() {
        this.characterField = new CharacterField();
        this.skillField = new SkillField();
    }

    /**
     * Change character card stance
     */
    public void changeStance() {
        this.characterField.changeStance();
    }

    /**
     * Set card in field
     * 
     * @param card card to set on field
     * @throws CardInFieldExist  if there has been card on field
     * @throws NoneCharacterCard if use skill card to none character card
     */
    public void setCardField(Summonedable card) throws CardInFieldExist, NoneCharacterCard {
        if (card instanceof Character) {
            this.characterField.setCard(card);
        } else if (card instanceof Skill) {
            if (this.characterField.getCard() == null) {
                throw new NoneCharacterCard();
            }
            this.skillField.setCard(card);
        }
    }

    /**
     * Remove character card
     */
    public void removeCharacterCard() {
        this.characterField.removeCard();
    }

    /**
     * Remove skill card
     */
    public void removeSkillCard() {
        this.skillField.removeCard();
    }

    @Override
    public String toString() {
        return characterField.getCard() + " " + characterField.getCurrentStance() + "\n" + skillField.getCard();
    }
}