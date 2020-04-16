package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.view.CardView;
import com.avatarduel.view.CharacterFieldView;
import com.avatarduel.view.FieldView;
import com.avatarduel.view.PlayerArena;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import com.avatarduel.gameplay.GameState;
import com.avatarduel.gameplay.Observer;
import com.avatarduel.gameplay.Phase;
import com.avatarduel.gameplay.Subject;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.player.Player;

/**
 * Class that responsible as controller of model (player) and view (playerArena)
 */
public class PlayerController implements Observer {
    private Player player;
    private PlayerArena playerArena;
    private Subject gameplay;
    private int id;
    private static CardView cardToBeMove;

    public PlayerController(Player player, int id, boolean isMirror) {
        this.player = player;
        this.id = id;
        this.playerArena = new PlayerArena(isMirror);
        this.playerArena.setFieldClickHandler(e -> {
            if (this.gameplay.getUpdate().getPhase() == Phase.MAIN) {
                if (cardToBeMove != null) {
                    Card card = cardToBeMove.getCard();
                    if ((card instanceof Character && e.getSource() instanceof CharacterFieldView
                            && player.getPowerElementCanUse(card.getElement()) >= ((Character) card).getPower())
                            || (card instanceof Skill && e.getSource() instanceof FieldView
                                    && player.getPowerElementCanUse(card.getElement()) >= ((Skill) card).getPower())) {
                        // TODO Mengubah di modelnya juga
                        cardToBeMove.setBorder(Color.BLACK);
                        ((FieldView) e.getSource()).getChildren().add(cardToBeMove);
                        cardToBeMove = null;
                    }
                }
            }
        });

        this.playerArena.setNextButtonHandler(e -> {
            if (this.gameplay.getUpdate().equals(Phase.MAIN, id) || this.gameplay.getUpdate().equals(Phase.BATTLE, id)
                    || this.gameplay.getUpdate().equals(Phase.END, (id + 1) % 2)) {
                this.gameplay.update();
            }
        });
    }

    /**
     * Set subject of gameplay
     */
    @Override
    public void setSubject(Subject sub) {
        this.gameplay = sub;
    }

    /**
     * Update method when there is update from gameplay
     */
    @Override
    public void update() {
        GameState gameState = this.gameplay.getUpdate();
        if (gameState.getTurn() == this.id) { // turn pemain
            if (gameState.getPhase() == Phase.DRAW) {
                this.player.setUpDrawPhase();
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() != Phase.END) {
                this.playerArena.setIsVisibleNextButton(true, "Done phase " + gameState.getPhase());
            } else if (gameState.getPhase() == Phase.END) {
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(false, null);
            }
        } else {
            if (gameState.getPhase() == Phase.DRAW) {
                this.playerArena.setFaceCardInHand(false);
            } else if (gameState.getPhase() == Phase.END) {
                this.playerArena.setIsVisibleNextButton(true, "Ready");
            }
        }
    }

    /**
     * Return the view of player arena
     * 
     * @return view of player arena
     */
    public PlayerArena getPlayerArena() {
        return playerArena;
    }

    /**
     * Set the player deck
     * 
     * @param deck stack of card that represent the deck
     */
    public void setDeck(Stack<Card> deck) {
        this.player.setDeck(deck);
        this.playerArena.setDeck(deck);
        this.playerArena.setDeckClickHandler(e -> {
            if (gameplay.getUpdate().equals(Phase.DRAW, id) && player.getTotalCardInDeck() != 0) {
                drawCard();
                gameplay.update();
            }
        });
    }

    /**
     * Set mouse over event on all card in deck. The card will be hover in
     * hoverSpace
     * 
     * @param hoverSpace space for the card hover
     */
    public void setDeckCardHover(StackPane hoverSpace) {
        this.playerArena.setDeckCardHover(hoverSpace);
    }

    public int getTotalDeckCard() {
        return this.player.getTotalDeckCard();
    }

    /**
     * Prosedure that draw 7 card in the first game
     */
    public void firstDrawCard() {
        for (int i = 0; i < 7; ++i) {
            this.drawCard();
        }
    }

    /**
     * Procedure that draw a card from deck
     */
    public void drawCard() {
        this.player.drawCard();
        this.playerArena.drawCard(e -> {
            if (this.gameplay.getUpdate().equals(Phase.MAIN, id)) {
                if (e.getClickCount() == 2 && cardToBeMove != null) {
                    this.player.removeCardInHand(cardToBeMove.getCard());
                    this.playerArena.addThrowPlaceCard(cardToBeMove);
                    cardToBeMove.setBorder(Color.BLACK);
                    cardToBeMove = null;
                } else {
                    boolean isNew = true;
                    if (cardToBeMove != null) {
                        System.out.println(cardToBeMove.getCard());
                        cardToBeMove.setBorder(Color.BLACK);
                        if (e.getSource().equals(cardToBeMove)) {
                            isNew = false;
                            if (cardToBeMove.getCard() instanceof Land) {
                                try {
                                    this.player.useCard(cardToBeMove.getCard());
                                    this.playerArena.addThrowPlaceCard(cardToBeMove);
                                } catch (Exception err) {
                                    new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
                                }
                            }
                            cardToBeMove = null;
                        }
                    }

                    if (isNew) {
                        cardToBeMove = (CardView) e.getSource();
                        cardToBeMove.setBorder(Color.RED);
                    }
                }
            }
        });
    }
}