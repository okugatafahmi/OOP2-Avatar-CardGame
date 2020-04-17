package com.avatarduel.gameplay;

import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.FieldPos;
import com.avatarduel.model.field.Field.Type;

public interface GlobalField {

    /**
     * Get card on field
     * 
     * @param type     field type
     * @param fieldPos field's position
     * @return card on specified player's column's field
     */
    public Summonedable getCardAtField(Type type, FieldPos fieldPos);

    /**
     * Remove card on field
     * 
     * @param type     field type
     * @param fieldPos field's position
     */
    public void removeCardAtField(Type type, FieldPos fieldPos);

    /**
     * Attach skill to character at fieldPos
     * 
     * @param skillPos skill card position to attach
     * @param fieldPos character field's position to be attached
     */
    public void attachSkill(FieldPos skillPos, FieldPos fieldPos);

    /**
     * Remove character's skill at field
     *
     * @param skillPos skill's position
     * @param fieldPos character's position to remove its skill
     */
    public void removeSkillOfCharacterAtField(FieldPos skillPos, FieldPos fieldPos);
}