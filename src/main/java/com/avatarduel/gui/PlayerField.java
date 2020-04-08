package com.avatarduel.gui;

/**
 * Class yang bertanggung jawab sebagai 1 arena player
 */
import com.avatarduel.model.card.*;
import com.avatarduel.model.card.Character;

public class PlayerField {
    private CharacterField[] characterFields;
    private Field[] skillFields;

    public PlayerField() {
        characterFields = new CharacterField[8];
        skillFields = new Field[8];

        for (int i = 0; i < characterFields.length; i++) {
            characterFields[i] = null;
        };

        for (int i = 0; i < skillFields.length; i++) {
            skillFields[i] = null;
        }
    };

    public Field getSkillField(int idx) throws Exception {
        if (idx < 0 || idx > skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }
        if (skillFields[idx] == null) {
            throw new Exception("No skill card at requested position");
        }
        return skillFields[idx];
    };

    public CharacterField getCharacterField(int idx) throws Exception {
        if (idx < 0 || idx > characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }
        if (characterFields[idx] == null) {
            throw new Exception("No character card at requested position");
        }
        return characterFields[idx];
    };

    public void setCharacterField(int idx, CharacterField charField) throws Exception {
        if (idx < 0 || idx > characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (characterFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.characterFields[idx] = charField;
    }

    public void setCharacterField(int idx, Character charCard) throws Exception {
        if (idx < 0 || idx > characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (characterFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.characterFields[idx] = new CharacterField(charCard);
    }

    public void setSkillField(int idx, Field skillField) throws Exception {
        if (idx < 0 || idx > skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (skillFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.skillFields[idx] = skillField;
    }

    public void setSkillField(int idx, Skill skillCard) throws Exception {
        if (idx < 0 || idx > skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (skillFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.skillFields[idx] = new Field(skillCard);
    }
}