package com.avatarduel.view;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class yang bertanggung jawab sebagai 1 arena player
 */
public class PlayerArena extends GridPane {
    private CharacterFieldView[] characterFields;
    private FieldView[] skillFields;
    private LandStatus landStatus;
    private CardInHand cardInHand;
    private StackPane deck;
    private StackPane throwPlace;
    private GridPane statusPlayer;
    private boolean inHandFaceUp;
    private Button nextButton;

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
        statusPlayer = new GridPane();
        nextButton = new Button();
        statusPlayer.getChildren().add(new Text("Status Pemain"));

        nextButton.setVisible(false);
        nextButton.setAlignment(Pos.CENTER);
        deck = new StackPane();
        deck.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        deck.setAlignment(Pos.CENTER);
        deck.setMinSize(64, 91);
        deck.setMaxSize(64, 91);
        deck.getChildren().add(new Text("Deck"));

        throwPlace = new StackPane();
        throwPlace.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        throwPlace.setAlignment(Pos.CENTER);
        throwPlace.setMinSize(64, 91);
        throwPlace.setMaxSize(64, 91);
        throwPlace.getChildren().add(new Text("Throw"));

        int rowInHand = ((isMirror) ? 0 : 1);
        int inc = ((isMirror) ? 1 : -1);

        GridPane container = new GridPane();

        int row = ((isMirror) ? 0 : 3);
        container.add(statusPlayer, 0, row, 2, 1);
        row += inc;
        container.add(deck, 0, row);
        container.add(throwPlace, 1, row);
        row += inc;
        container.add(nextButton, 0, row, 2, 1);
        row += inc;
        container.add(landStatus, 0, row, 2, 1);
        container.setAlignment(Pos.CENTER);
        container.setHgap(40);
        container.setVgap(10);
        container.setPadding(new Insets(40, 5, 40, 5));

        this.add(cardInHand, 0, rowInHand);
        rowInHand += inc;
        this.add(arena, 0, rowInHand);
        this.add(container, 1, 0, 1, 2);
        this.setHgap(20);
        this.setAlignment(Pos.CENTER);
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
    public void addInHand(CardView cardView, EventHandler<MouseEvent> cardInHandClickHandler) {
        this.cardInHand.getChildren().add(cardView);
        if (!inHandFaceUp) {
            cardView.faceDown();
        } else {
            cardView.faceUp();
        }
        cardView.addEventHandler(MouseEvent.MOUSE_CLICKED, cardInHandClickHandler);
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
     * 
     * @param cardInHandClickHandler handler when click card in hand
     */
    public void drawCard(EventHandler<MouseEvent> cardInHandClickHandler) {
        Node node = deck.getChildren().get(deck.getChildren().size() - 1);
        addInHand((CardView) node, cardInHandClickHandler);
    }

    /**
     * Set deck click handler
     * 
     * @param eventHandler
     */
    public void setDeckClickHandler(EventHandler<MouseEvent> eventHandler) {
        deck.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    /**
     * Set mouse over event on all card in deck
     * 
     * @param hoverHandler hover handler
     */
    public void setDeckCardHover(StackPane hoverSpace) {
        deck.getChildren().stream().forEach(card -> {
            card.setOnMouseEntered(e -> {
                if (((CardView) card).getIsFaceUp()) {
                    CardView cardHover = new CardView(((CardView) card).getCard());
                    cardHover.setScaleX(4);
                    cardHover.setScaleY(4);
                    hoverSpace.getChildren().add(cardHover);
                }
            });
            card.setOnMouseExited(e -> {
                if (((CardView) card).getIsFaceUp()) {
                    hoverSpace.getChildren().remove(0);
                }
            });
        });
    }

    public void setFieldClickHandler(EventHandler<MouseEvent> eventHandler) {
        for (CharacterFieldView characterFieldView : characterFields) {
            characterFieldView.setClickHandler(eventHandler);
        }
        for (FieldView skillFieldView : skillFields) {
            skillFieldView.setClickHandler(eventHandler);
        }
    }

    /**
     * Set next button to be visible or not
     * 
     * @param isVisible boolean if the button visible or not
     * @param text      button's text
     */
    public void setIsVisibleNextButton(boolean isVisible, String text) {
        if (isVisible) {
            nextButton.setText(text);
        }
        nextButton.setVisible(isVisible);
    }

    /**
     * Set next button handler
     * 
     * @param handler handler when next button clicked
     */
    public void setNextButtonHandler(EventHandler<ActionEvent> handler) {
        nextButton.setOnAction(handler);
    }

    /**
     * Add card on throw place
     * 
     * @param card card will be throw
     */
    public void addThrowPlaceCard(CardView card) {
        throwPlace.getChildren().add(card);
    }
}