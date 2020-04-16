package com.avatarduel.view;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.Card;

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
    private ColumnFieldView[] columnFieldViews;
    private LandStatus landStatus;
    private CardInHand cardInHand;
    private StackPane deck;
    private StackPane throwPlace;
    private GridPane statusPlayer;
    private boolean inHandFaceUp;
    private Button nextButton;

    /**
     * Constructor of player's arena
     * 
     * @param isMirror boolean whether the arena is mirrorred (on above)
     */
    public PlayerArena(boolean isMirror) {
        columnFieldViews = new ColumnFieldView[8];
        inHandFaceUp = false;

        GridPane arena = new GridPane();
        for (int i = 0; i < columnFieldViews.length; i++) {
            columnFieldViews[i] = new ColumnFieldView(i, isMirror);
            arena.addColumn(i, columnFieldViews[i]);
        }
        arena.setVgap(10);
        arena.setHgap(10);
        arena.setPadding(new Insets(20, 4, 20, 4));
        arena.setAlignment(Pos.CENTER);

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

    /**
     * Procedure that will add card in hand visually
     * 
     * @param cardView               card view will be added
     * @param cardInHandClickHandler click handler when card in hand
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
     * Set card at field
     * 
     * @param cardView card view to set
     * @param column   column of field
     */
    public void setCardAtField(CardView cardView, int column) {
        columnFieldViews[column].setCardView(cardView);
    }

    public void changeStance(int column) {
        columnFieldViews[column].changeStance();
    }

    /**
     * Set deck click handler
     * 
     * @param eventHandler click handler for deck
     */
    public void setDeckClickHandler(EventHandler<MouseEvent> eventHandler) {
        deck.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    /**
     * Set mouse over event on all card in deck
     * 
     * @param hoverSpace hover space
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

    /**
     * Set field click hander
     * 
     * @param characterFieldEventHandler event handler for character field
     * @param skillFieldEventHandler     event handler for skill field
     */
    public void setFieldClickHandler(EventHandler<MouseEvent> characterFieldEventHandler,
            EventHandler<MouseEvent> skillFieldEventHandler) {
        for (ColumnFieldView columnFieldView : columnFieldViews) {
            columnFieldView.setClickHandler(characterFieldEventHandler, skillFieldEventHandler);
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
        if (card.getTransforms().size() == 1) {
            card.getTransforms().remove(0);
        }
        throwPlace.getChildren().add(card);
    }
}