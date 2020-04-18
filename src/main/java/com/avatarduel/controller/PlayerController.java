package com.avatarduel.controller;

import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import com.avatarduel.gameplay.GameState;
import com.avatarduel.gameplay.GlobalField;
import com.avatarduel.gameplay.Observer;
import com.avatarduel.gameplay.Phase;
import com.avatarduel.gameplay.Subject;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Destroy;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.CharacterField;
import com.avatarduel.model.field.FieldPos;
import com.avatarduel.model.field.PlaceCardException;
import com.avatarduel.model.field.Field.Type;
import com.avatarduel.model.player.DeckCardEmpty;
import com.avatarduel.model.player.Player;
import com.avatarduel.view.card.CardView;
import com.avatarduel.view.player.PlayerArena;

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

    public PlayerController(Player player, boolean isMirror) {
        this.player = player;
        this.id = player.getId();
        this.playerArena = new PlayerArena(isMirror);
        this.playerArena.setNextButtonHandler(e -> {
            if (this.gameplay.getUpdate().equals(Phase.READY, id) || this.gameplay.getUpdate().equals(Phase.MAIN, id)
                    || this.gameplay.getUpdate().equals(Phase.BATTLE, id)
                    || this.gameplay.getUpdate().equals(Phase.END, (id + 1) % 2)) {
                this.gameplay.update();
            }
        });
    }

    /**
     * @return the cardToBeMove
     */
    public static CardView getCardToBeMove() {
        return cardToBeMove;
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
    public void update() throws DeckCardEmpty {
        GameState gameState = this.gameplay.getUpdate();
        if (gameState.getTurn() == this.id) { // turn pemain
            if (gameState.getPhase() == Phase.READY) {
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(true, "Ready");
            } else if (gameState.getPhase() == Phase.FINISHED) {
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() == Phase.DRAW) {
                this.player.setupDrawPhase();
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() != Phase.END) {
                if (gameState.getPhase()==Phase.BATTLE && cardToBeMove!=null) {
                    cardToBeMove.setBorder(Color.BLACK);
                    cardToBeMove = null;
                }
                this.playerArena.setIsVisibleNextButton(true, "Done " + gameState.getPhase() + " phase");
            } else if (gameState.getPhase() == Phase.END) {
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(false, null);
            }
        } else {
            if (gameState.getPhase() == Phase.READY) {
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() == Phase.FINISHED) {
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() == Phase.DRAW) {
                this.playerArena.setFaceCardInHand(false);
            } else if (gameState.getPhase() == Phase.END) {
                this.playerArena.setIsVisibleNextButton(true, "Ready");
            }
        }
    }

    /**
     * Set global field
     * 
     * @param globalField global field
     */
    public void setGlobalField(GlobalField globalField) {
        this.player.setGlobalField(globalField);
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
     * 
     * @return player's hp
     */
    public int getHp() {
        return this.player.getHp();
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

    /**
     * Get player total deck card when start game
     * 
     * @return total deck card when start game
     */
    public int getTotalDeckCard() {
        return this.player.getTotalDeckCard();
    }

    /**
     * Get total character in field
     * 
     * @return total character in field
     */
    public int getTotalCharacterInField() {
        return this.player.getTotalCharacterInField();
    }

    /**
     * Reduce player's hp based the amount of damage. If {@code damage > hp}, hp
     * will be 0
     * 
     * @param damage the amount damage of the player got
     */
    public void getDamage(int damage) {
        this.player.getDamage(damage);
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
     * Change stance of character field
     * 
     * @param fieldColumn field's column
     */
    public void changeStance(int fieldColumn) {
        this.player.changeStance(fieldColumn);
        this.playerArena.changeStance(fieldColumn);
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
                                // hapus click handler nya
                                cardToBeMove.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
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

    /**
     * Set field click handler
     * 
     * @param characterFieldEventHandler handler for character field
     * @param skillFieldEventHandler     handler for skill field
     */
    public void setFieldClickHandler(EventHandler<MouseEvent> characterFieldEventHandler,
            EventHandler<MouseEvent> skillFieldEventHandler) {
        this.playerArena.setFieldClickHandler(characterFieldEventHandler, skillFieldEventHandler);
    }

    /**
     * Get character field at spesifed column
     * 
     * @param column character field's column
     * @return the field
     */
    public CharacterField getCharacterField(int column) {
        return this.player.getCharacterField(column);
    }

    public Summonedable getCardAtField(Type type, int column) {
        if (type == Type.CHARACTER) {
            return this.player.getCharacterCardAtField(column);
        } else if (type == Type.SKILL) {
            return this.player.getSkillCardAtField(column);
        } else {
            return null;
        }
    }

    public void removeCardAtField(Type type, int column) {
        // TODO hapus println
        if (type == Type.CHARACTER) {
            if (this.player.removeCharacterCardAtField(column) != null) {
                this.playerArena.removeCardAtField(type, column);
                System.out.println(this.player);
            }
        } else if (type == Type.SKILL) {
            if (this.player.removeSkillCardAtField(column) != null) {
                this.playerArena.removeCardAtField(type, column);
                System.out.println(this.player);
            }
        }
    }

    public void removeSkillOfCharacterAtField(FieldPos skillPos, int column) {
        this.player.removeSkillOfCharacterAtField(skillPos, column);
    }

    public void attachSkill(FieldPos skillPos, int column) {
        this.player.attachSkill(skillPos, column);
    }

    public void summonCharacterCard(CardView cardView, int fieldColumn) {
        if (cardView == null)
            return;
        if (!(cardView.getCard() instanceof Character))
            return;
        Character card = (Character) cardView.getCard();
        int index = -1;
        // kalau card to be move di in hand
        try {
            index = this.player.useCard(card);
            this.player.setCharacterCardAtField(card, fieldColumn);
            // TODO Hapus println
            System.out.println(this.player);
            this.playerArena.setCharacterCardAtField(cardView, fieldColumn);
            cardView.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
        } catch (PlaceCardException err) {
            this.player.insertCardInHand(index, card);
            new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
        } catch (Exception err) {
            new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
        } finally {
            cardToBeMove.setBorder(Color.BLACK);
            cardToBeMove = null;
        }
    }

    public void summonSkillCard(CardView cardView, FieldPos fieldPos) {
        if (cardView == null)
            return;
        if (!(cardView.getCard() instanceof Skill))
            return;
        Skill card = (Skill) cardView.getCard();
        int index = -1;
        // kalau card to be move di in hand
        try {
            index = this.player.useCard(card);
            int column = this.player.setSkillCardAtField(card, fieldPos);
            // TODO Hapus println
            System.out.println(this.player);
            if (card instanceof Destroy) {
                this.playerArena.addThrowPlaceCard(cardView);
            } else {
                this.playerArena.setSkillCardAtField(cardView, column);
                cardView.removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
            }
        } catch (PlaceCardException err) {
            this.player.insertCardInHand(index, card);
            new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
        } catch (Exception err) {
            new Alert(AlertType.ERROR, err.getMessage(), ButtonType.OK).showAndWait();
        } finally {
            cardToBeMove.setBorder(Color.BLACK);
            cardToBeMove = null;
        }
    }
}