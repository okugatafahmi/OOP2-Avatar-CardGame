package com.avatarduel.view.player;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Element;
import com.avatarduel.model.field.Field.Type;

import static com.avatarduel.model.player.Player.N_COLUMN;
import com.avatarduel.view.card.CardView;
import com.avatarduel.view.field.CharacterFieldView;
import com.avatarduel.view.field.SkillFieldView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class yang bertanggung jawab sebagai 1 arena player
 */
public class PlayerArena extends GridPane {
    private CharacterFieldView[] characterFieldViews;
    private SkillFieldView[] skillFieldViews;
    private LandStatus landStatus;
    private CardInHand cardInHand;
    private StackPane deck;
    private StackPane throwPlace;
    // private GridPane statusPlayer;
    private StatusPlayer statusPlayer;
    private boolean inHandFaceUp;
    private Button nextButton;
    private CardView cardToBeMove;

    /**
     * Constructor of player's arena
     * 
     * @param isMirror boolean whether the arena is mirrorred (on above)
     */
    public PlayerArena(boolean isMirror) {
        characterFieldViews = new CharacterFieldView[8];
        skillFieldViews = new SkillFieldView[8];
        inHandFaceUp = false;

        GridPane arena = new GridPane();
        for (int i = 0; i < N_COLUMN; i++) {
            characterFieldViews[i] = new CharacterFieldView(i);
            skillFieldViews[i] = new SkillFieldView(i);
            arena.add(characterFieldViews[i], i, ((isMirror) ? 1 : 0));
            arena.add(skillFieldViews[i], i, ((isMirror) ? 0 : 1));
        }
        arena.setVgap(10);
        arena.setHgap(10);
        arena.setPadding(new Insets(20, 4, 20, 4));
        arena.setAlignment(Pos.CENTER);

        landStatus = new LandStatus();
        cardInHand = new CardInHand();
        statusPlayer = new StatusPlayer();
        nextButton = new Button();
        // statusPlayer.getChildren().add(new Text("Status Pemain"));

        nextButton.setVisible(false);
        nextButton.setAlignment(Pos.CENTER);
        nextButton.setPadding(new Insets(5, 10, 5, 10));
        deck = new StackPane();
        deck.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        deck.setAlignment(Pos.CENTER);
        deck.setMinSize(64, 91);
        deck.setMaxSize(64, 91);

        throwPlace = new StackPane();
        throwPlace.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        throwPlace.setAlignment(Pos.CENTER);
        throwPlace.setMinSize(64, 91);
        throwPlace.setMaxSize(64, 91);
        throwPlace.getChildren().add(new Text("Throw"));
        GridPane.setHalignment(throwPlace, HPos.RIGHT);

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
        GridPane.setHalignment(nextButton, HPos.CENTER);
        row += inc;
        container.add(landStatus, 0, row, 2, 1);
        container.setAlignment(Pos.CENTER);
        container.setHgap(40);
        container.setVgap(5);
        container.setPadding(new Insets(40, 5, 40, 5));

        this.add(cardInHand, 0, rowInHand);
        rowInHand += inc;
        this.add(arena, 0, rowInHand);
        this.add(container, 1, 0, 1, 2);
        this.setHgap(20);
        this.setVgap(10);
        if (isMirror) {
            this.getRowConstraints().addAll(new RowConstraints(100), new RowConstraints(200));
            this.setAlignment(Pos.BOTTOM_CENTER);
            this.setPadding(new Insets(0, 3, 15, 3));
        } else {
            this.getRowConstraints().addAll(new RowConstraints(200), new RowConstraints(100));
            this.setAlignment(Pos.TOP_CENTER);
            this.setPadding(new Insets(15, 3, 0, 3));
        }
    }

    /**
     * @param cardToBeMove the cardToBeMove to set
     */
    public void setCardToBeMove(CardView cardToBeMove) {
        if (cardToBeMove == null) {
            if (this.cardToBeMove != null) {
                this.cardToBeMove.setBorder(Color.BLACK);
            }
        } else if (this.cardToBeMove != cardToBeMove) {
            if (this.cardToBeMove != null) {
                this.cardToBeMove.setBorder(Color.BLACK);
            }
            cardToBeMove.setBorder(Color.RED);
        }
        this.cardToBeMove = cardToBeMove;
    }

    /**
     * @return the cardToBeMove
     */
    public CardView getCardToBeMove() {
        return cardToBeMove;
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
     * Update power can use status
     * @param element   card's element
     * @param powerCanUse number power can use
     */
    public void updatePowerCanUse(Element element, int powerCanUse) {
        this.landStatus.updatePowerCanUse(element, powerCanUse);
    }

    /**
     * Update power total status
     * @param element   card's element
     * @param powerTotal number power total
     */
    public void updatePowerTotal(Element element, int powerTotal) {
        this.landStatus.updatePowerTotal(element, powerTotal);
    }

    /**
     * Update player's name in player status
     * @param name player's name
     */
    public void updatePlayerName(String name){
        this.statusPlayer.updatePlayerName(name);
    }

    /**
     * Update player's hp in player status
     * @param hp player's hp
     */
    public void updatePlayerHp(int hp) {
        this.statusPlayer.updatePlayerHp(hp);
    }

    /**
     * Set card at field
     *
     * @param cardView card view to set
     * @param column   column of field
     */
    public void setCharacterCardAtField(CardView cardView, int column) {
        characterFieldViews[column].setCardView(cardView);
    }

    /**
     * Set skill card at field
     * 
     * @param cardView card view to set
     * @param column   column of field
     */
    public void setSkillCardAtField(CardView cardView, int column) {
        skillFieldViews[column].setCardView(cardView);
    }

    /**
     * Remove card at field
     * 
     * @param type   field type
     * @param column column of field
     */
    public void removeCardAtField(Type type, int column) {
        if (type == null) {
            return;
        }
        CardView cardView;
        if (type == Type.CHARACTER) {
            cardView = characterFieldViews[column].getCardView();
        } else {
            cardView = skillFieldViews[column].getCardView();
        }
        if (cardView != null) {
            addThrowPlaceCard(cardView);
        }
    }

    /**
     * Change stance of character field with specified column
     * 
     * @param column character field's column
     */
    public void changeStance(int column) {
        characterFieldViews[column].changeStance();
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
        for (CharacterFieldView characterFieldView : characterFieldViews) {
            characterFieldView.addEventHandler(MouseEvent.MOUSE_CLICKED, characterFieldEventHandler);
        }
        for (SkillFieldView skillFieldView : skillFieldViews) {
            skillFieldView.addEventHandler(MouseEvent.MOUSE_CLICKED, skillFieldEventHandler);
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

    public void showErrorAlert(String msg) {
        Alert alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}