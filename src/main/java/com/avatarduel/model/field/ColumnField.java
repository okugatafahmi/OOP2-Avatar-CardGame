package com.avatarduel.model.field;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;

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
     * Set character card in field
     * 
     * @param characterFieldCard the characterFieldCard to set
     * @throws CardInFieldExist if the field has had card
     */
    public void setCharacterField(Character characterFieldCard) throws CardInFieldExist {
        this.characterField.setCard(characterFieldCard);
    }

    /**
     * Set character card in field
     * 
     * @param skillFieldCard the skillFieldCard to set
     * @throws CardInFieldExist if the field has had card
     */
    public void setSkillField(Skill skillFieldCard) throws CardInFieldExist {
        this.skillField.setCard(skillFieldCard);
    }
}