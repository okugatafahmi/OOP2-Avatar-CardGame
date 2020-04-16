package com.avatarduel.controller;

import java.util.NoSuchElementException;
import java.util.Stack;

import com.avatarduel.view.CardView;
import com.avatarduel.view.CharacterFieldView;
import com.avatarduel.view.PlayerArena;
import com.avatarduel.view.SkillFieldView;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import com.avatarduel.model.card.Summonedable;
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
    private final EventHandler<MouseEvent> cardOnClick = this::cardOnClick;

    public PlayerController(Player player, int id, boolean isMirror) {
        this.player = player;
        this.id = id;
        this.playerArena = new PlayerArena(isMirror);
        this.playerArena.setFieldClickHandler(e -> {
            // TODO Attach skill ke musuh
            if (this.gameplay.getUpdate().equals(Phase.MAIN, id)) {
                int fieldColumn = ((CharacterFieldView) e.getSource()).getColumn();
                // Menaruh kartu di field
                if (cardToBeMove != null) {
                    if (cardToBeMove.getCard() instanceof Character) {
                        summonCard(cardToBeMove, fieldColumn);
                    }
                }
                else if (e.getButton() == MouseButton.SECONDARY) {
                    this.player.removeCharacterCardAtField(fieldColumn);
                    this.playerArena.addThrowPlaceCard(((CharacterFieldView) e.getSource()).getCardView());
                }
                else {
                    this.player.changeStance(fieldColumn);
                    this.playerArena.changeStance(fieldColumn);
                }
            }
        }, e -> {
            if (this.gameplay.getUpdate().equals(Phase.MAIN, id)) {
                int fieldColumn = ((SkillFieldView) e.getSource()).getColumn();
                // Menaruh kartu di field
                if (cardToBeMove != null) {
                    if (cardToBeMove.getCard() instanceof Skill) {
                        summonCard(cardToBeMove, fieldColumn);
                    }
                }
                else if (e.getButton() == MouseButton.SECONDARY) {
                    this.player.removeSkillCardAtField(fieldColumn);
                    this.playerArena.addThrowPlaceCard(((SkillFieldView) e.getSource()).getCardView());
                }
                else {
                    this.player.changeStance(fieldColumn);
                    this.playerArena.changeStance(fieldColumn);
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
        this.playerArena.drawCard(cardOnClick);
    }

    /**
     * Card on click handler when card in hand
     * 
     * @param e mouse event
     */
    private void cardOnClick(MouseEvent e) {
        if (this.gameplay.getUpdate().equals(Phase.MAIN, id)) {
            if (e.getButton() == MouseButton.SECONDARY) {
                CardView cardView = (CardView) e.getSource();
                this.player.removeCardInHand(cardView.getCard());
                this.playerArena.addThrowPlaceCard(cardView);
                cardView.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick); // hapus click handler nya
                if (cardView.equals(cardToBeMove)) {
                    cardToBeMove.setBorder(Color.BLACK);
                    cardToBeMove = null;
                }
            } else {
                boolean isNew = true;
                if (cardToBeMove != null) {
                    System.out.println(cardToBeMove.getCard());
                    cardToBeMove.setBorder(Color.BLACK);
                    if (e.getSource().equals(cardToBeMove)) {
                        isNew = false;
                        // menggunakan kartu land
                        if (cardToBeMove.getCard() instanceof Land) {
                            try {
                                this.player.useCard(cardToBeMove.getCard());
                                this.playerArena.addThrowPlaceCard(cardToBeMove);
                                cardToBeMove.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick); // hapus click handler nya
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
    }

    private void summonCard(CardView cardView, int fieldColumn) {
        Card card = cardView.getCard();
        // kalau card to be move di in hand
        try {
            if (!this.player.isCardInHand(card)) {
                throw new NoSuchElementException("There is no card " + card.getName());
            }
            this.player.setCardAtField((Summonedable) card, fieldColumn);
            this.player.useCard(card);
            // TODO Hapus println
            System.out.println(this.player);
            this.playerArena.setCardAtField(cardToBeMove, fieldColumn);
            cardToBeMove.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
        }
        catch (Exception err) {
            new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
        }
        finally {
            cardToBeMove.setBorder(Color.BLACK);
            cardToBeMove = null;
        }
    }
}