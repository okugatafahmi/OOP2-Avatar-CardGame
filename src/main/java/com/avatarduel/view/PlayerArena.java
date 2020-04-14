package com.avatarduel.view;

/**
 * Class yang bertanggung jawab sebagai 1 arena player
 */
import com.avatarduel.model.card.*;
import com.avatarduel.model.card.Character;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PlayerArena extends GridPane {
    private CharacterFieldView[] characterFields;
    private FieldView[] skillFields;
    private LandStatus landStatus;
    private CardInHand cardInHand;

    public PlayerArena(boolean isMirror) {
        characterFields = new CharacterFieldView[8];
        skillFields = new FieldView[8];

        GridPane arena = new GridPane();
        int rowField = ((isMirror) ? 1 : 0);
        for (int i = 0; i < characterFields.length; i++) {
            characterFields[i] = new CharacterFieldView();
            arena.add(characterFields[i], i, rowField);
        }

        for (int i = 0; i < skillFields.length; i++) {
            skillFields[i] = new FieldView();
            arena.add(skillFields[i], i, (rowField + 1) % 2);
        }
        arena.setVgap(10);
        arena.setHgap(10);
        arena.setPadding(new Insets(20, 4, 20, 4));

        landStatus = new LandStatus();
        cardInHand = new CardInHand();

        int rowInHand = ((isMirror) ? 0 : 2);
        int inc = ((isMirror) ? 1 : -1);
        super.add(cardInHand, 0, rowInHand);
        super.add(new Text("Ini buat gambar deck"), 1, rowInHand);
        rowInHand += inc;
        super.add(landStatus, 1, rowInHand);
        super.add(arena, 0, rowInHand);
        super.setHgap(20);
        super.setVgap(10);
        super.setAlignment(Pos.CENTER);
    }

    public FieldView getSkillField(int idx) throws Exception {
        if (idx < 0 || idx >= skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }
        if (skillFields[idx] == null) {
            throw new Exception("No skill card at requested position");
        }
        return skillFields[idx];
    }

    public CharacterFieldView getCharacterField(int idx) throws Exception {
        if (idx < 0 || idx >= characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }
        if (characterFields[idx] == null) {
            throw new Exception("No character card at requested position");
        }
        return characterFields[idx];
    }

    public void setCharacterField(int idx, CharacterFieldView charField) throws Exception {
        if (idx < 0 || idx >= characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (characterFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.characterFields[idx] = charField;
    }

    public void setCharacterField(int idx, Character charCard) throws Exception {
        if (idx < 0 || idx >= characterFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (characterFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        // this.characterFields[idx] = new CharacterFieldView(charCard);
    }

    public void setSkillField(int idx, FieldView skillField) throws Exception {
        if (idx < 0 || idx >= skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (skillFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        this.skillFields[idx] = skillField;
    }

    public void setSkillField(int idx, Skill skillCard) throws Exception {
        if (idx < 0 || idx >= skillFields.length) {
            throw new IndexOutOfBoundsException("Requested index not in Field");
        }

        if (skillFields[idx] != null) {
            throw new Exception("Field position already taken");
        }

        // this.skillFields[idx] = new FieldView(skillCard);
    }

    public void addInHand(CardView cardView){
        this.cardInHand.getChildren().add(cardView);
    }
}