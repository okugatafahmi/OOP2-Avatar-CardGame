package com.avatarduel.view;

import java.beans.EventHandler;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.*;
import com.avatarduel.model.card.Character;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Class yang bertanggung jawab sebagai 1 arena player
 */
public class PlayerArena extends GridPane {
    private CharacterFieldView[] characterFields;
    private FieldView[] skillFields;
    private LandStatus landStatus;
    private CardInHand cardInHand;
    private StackPane deck;
    private boolean inHandFaceUp;

    public PlayerArena(boolean isMirror) {
        characterFields = new CharacterFieldView[8];
        skillFields = new FieldView[8];
        inHandFaceUp = false;

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
        GridPane statusPlayer = new GridPane();
        deck = new StackPane();
        deck.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        statusPlayer.add(deck, 0, 0);
        super.add(statusPlayer, 1, rowInHand);
        rowInHand += inc;
        super.add(landStatus, 1, rowInHand);
        super.add(arena, 0, rowInHand);
        super.setHgap(20);
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

    /**
     * Procedure that will add card in hand visually
     * 
     * @param cardView card view will be added
     */
    public void addInHand(CardView cardView) {
        if (!inHandFaceUp) {
            cardView.faceDown();
        }
        else {
            cardView.faceUp();
        }
        this.cardInHand.getChildren().add(cardView);
    }

    /**
     * Set face card in hand
     * 
     * @param isFaceUp set true if the card in hand should be face up
     */
    public void setFaceCardInHand(boolean isFaceUp) {
        this.cardInHand.setFaceCard(isFaceUp);
        this.inHandFaceUp = isFaceUp;
    }

    /**
     * Set deck in view
     * 
     * @param cards card deck (stack of card)
     */
    public void setDeck(Stack<Card> cards) {
        List<CardView> cardViews = cards.stream().map(card -> new CardView(card)).collect(Collectors.toList());
        cardViews.stream().forEach(card -> {
            card.faceDown();
            deck.getChildren().add(card);
        });
    }

    /**
     * Draw card
     */
    public void drawCard() {
        Node node = deck.getChildren().get(deck.getChildren().size() - 1);
        addInHand((CardView) node);
    }

    public void setDeckEventHandler(javafx.event.EventHandler<MouseEvent> eventHandler) {
        deck.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}