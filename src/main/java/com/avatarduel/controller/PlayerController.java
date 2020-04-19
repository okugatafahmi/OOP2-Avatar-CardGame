package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.gameplay.GameState;
import com.avatarduel.gameplay.GlobalField;
import com.avatarduel.gameplay.Observer;
import com.avatarduel.gameplay.Phase;
import com.avatarduel.gameplay.Subject;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Destroy;
import com.avatarduel.model.card.Element;
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

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Class that responsible as controller of model (player) and view (playerArena)
 * if the view set
 */
public class PlayerController implements Observer {
    private Player player;
    private PlayerArena playerArena;
    private Subject gameplay;
    private int id;
    private final EventHandler<MouseEvent> cardOnClick = this::cardOnClick;

    public PlayerController(int id) {
        this.player = new Player(id);
        this.id = id;
    }

    /**
     * Set player's name
     * 
     * @param name name
     */
    public void setName(String name) {
        this.player.setName(name);
    }

    /**
     * Set player's total deck card
     * 
     * @param totalDeckCard amount total deck card in the first game
     */
    public void setTotalDeckCard(int totalDeckCard) {
        this.player.setTotalDeckCard(totalDeckCard);
    }

    /**
     * @return the cardToBeMove
     */
    public Card getCardToBeMove() {
        return this.player.getCardToBeMove();
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
        if (gameState.equals(Phase.DRAW, this.id)) {
            this.player.setupDrawPhase();
        } else if (gameState.equals(Phase.BATTLE, this.id) && this.player.getCardToBeMove() != null) {
            this.player.setCardToBeMove(null);
            if (this.playerArena != null) {
                this.playerArena.setCardToBeMove(null);
            }
        }
        // update arena kalau ada
        if (this.playerArena == null)
            return;
        if (gameState.getTurn() == this.id) { // turn pemain
            if (gameState.getPhase() == Phase.READY) {
                this.updateAllPower();
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(true, "Ready");
            } else if (gameState.getPhase() == Phase.FINISHED) {
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() == Phase.DRAW) {
                this.updateAllPower();
                this.playerArena.setFaceCardInHand(true);
                this.playerArena.setIsVisibleNextButton(false, null);
            } else if (gameState.getPhase() != Phase.END) {
                this.playerArena.setIsVisibleNextButton(true, "Done " + gameState.getPhase() + " phase");
            } else if (gameState.getPhase() == Phase.END) {
                this.playerArena.setFaceCardInHand(false);
                this.playerArena.setIsVisibleNextButton(false, null);
            }
        } else {
            if (gameState.getPhase() == Phase.READY) {
                this.updateAllPower();
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
     * @param playerArena the playerArena to set
     */
    public void setPlayerArena(PlayerArena playerArena) {
        this.playerArena = playerArena;
        this.playerArena.setNextButtonHandler(e -> {
            if (this.gameplay.getUpdate().equals(Phase.READY, id) || this.gameplay.getUpdate().equals(Phase.MAIN, id)
                    || this.gameplay.getUpdate().equals(Phase.BATTLE, id)
                    || this.gameplay.getUpdate().equals(Phase.END, (id + 1) % 2)) {
                this.gameplay.update();
            }
        });
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
        if (this.playerArena != null) {
            this.playerArena.setDeck(deck);
            this.playerArena.setDeckClickHandler(e -> {
                if (gameplay.getUpdate().equals(Phase.DRAW, id) && player.getTotalCardInDeck() != 0) {
                    drawCard();
                    gameplay.update();
                }
            });
        }
    }

    /**
     * Set mouse over event on all card in deck. The card will be hover in
     * hoverSpace
     * 
     * @param hoverSpace space for the card hover
     */
    public void setDeckCardHover(StackPane hoverSpace) {
        if (this.playerArena != null) {
            this.playerArena.setDeckCardHover(hoverSpace);
        }
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
        if (this.playerArena != null) {
            this.playerArena.drawCard(cardOnClick);
        }
    }

    /**
     * Update all power gui
     */
    public void updateAllPower() {
        if (this.playerArena != null) {
            for (Element element : Element.values()) {
                this.playerArena.updatePowerCanUse(element, this.player.getPowerElementCanUse(element));
                this.playerArena.updatePowerTotal(element, this.player.getPowerElementTotal(element));
            }
        }
    }

    /**
     * Change stance of character field
     * 
     * @param fieldColumn field's column
     */
    public void changeStance(int fieldColumn) {
        this.player.changeStance(fieldColumn);
        if (this.playerArena != null) {
            this.playerArena.changeStance(fieldColumn);
        }
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
                if (cardView == this.playerArena.getCardToBeMove()) {
                    this.player.setCardToBeMove(null);
                    this.playerArena.setCardToBeMove(null);
                }
            } else {
                boolean isNew = true;
                if (this.player.getCardToBeMove() != null) {
                    System.out.println(this.player.getCardToBeMove());
                    if (e.getSource() == this.playerArena.getCardToBeMove()) {
                        isNew = false;
                        // menggunakan kartu land
                        if (this.player.getCardToBeMove() instanceof Land) {
                            try {
                                this.player.useCard(this.player.getCardToBeMove());
                                this.playerArena.addThrowPlaceCard(this.playerArena.getCardToBeMove());
                                // hapus click handler nya
                                this.playerArena.getCardToBeMove().removeEventHandler(MouseEvent.MOUSE_CLICKED,
                                        cardOnClick);
                                this.playerArena.updatePowerCanUse(this.player.getCardToBeMove().getElement(),
                                        this.player.getPowerElementCanUse(this.player.getCardToBeMove().getElement()));
                                this.playerArena.updatePowerTotal(this.player.getCardToBeMove().getElement(),
                                        this.player.getPowerElementTotal(this.player.getCardToBeMove().getElement()));
                            } catch (Exception err) {
                                errorHandler(err.getMessage());
                            }
                        }
                        this.player.setCardToBeMove(null);
                        this.playerArena.setCardToBeMove(null);
                    }
                }

                if (isNew) {
                    this.player.setCardToBeMove(((CardView) e.getSource()).getCard());
                    this.playerArena.setCardToBeMove((CardView) e.getSource());
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
        if (this.playerArena != null) {
            this.playerArena.setFieldClickHandler(characterFieldEventHandler, skillFieldEventHandler);
        }
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
            if (this.player.removeCharacterCardAtField(column) != null && this.playerArena != null) {
                this.playerArena.removeCardAtField(type, column);
            }
            System.out.println(this.player);
        } else if (type == Type.SKILL) {
            if (this.player.removeSkillCardAtField(column) != null && this.playerArena != null) {
                this.playerArena.removeCardAtField(type, column);
            }
            System.out.println(this.player);
        }
    }

    public void removeSkillOfCharacterAtField(FieldPos skillPos, int column) {
        this.player.removeSkillOfCharacterAtField(skillPos, column);
    }

    public void attachSkill(FieldPos skillPos, int column) {
        this.player.attachSkill(skillPos, column);
    }

    /**
     * Summon character card from player's attribute cardToBeMove
     * 
     * @param fieldColumn field's column
     */
    public void summonCharacterCard(int fieldColumn) {
        if (this.getCardToBeMove() == null)
            return;
        if (!(this.getCardToBeMove() instanceof Character))
            return;
        Character card = (Character) this.getCardToBeMove();
        int index = -1;
        // kalau card to be move di in hand
        try {
            index = this.player.useCard(card);
            this.player.setCharacterCardAtField(card, fieldColumn);
            // TODO Hapus println
            System.out.println(this.player);
            if (this.playerArena != null) {
                this.playerArena.updatePowerCanUse(card.getElement(),
                        this.player.getPowerElementCanUse(card.getElement()));
                this.playerArena.setCharacterCardAtField(this.playerArena.getCardToBeMove(), fieldColumn);
                this.playerArena.getCardToBeMove().removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
            }
        } catch (PlaceCardException err) {
            this.player.insertCardInHand(index, card);
            errorHandler(err.getMessage());
        } catch (Exception err) {
            errorHandler(err.getMessage());
        } finally {
            this.player.setCardToBeMove(null);
            if (this.playerArena != null) {
                this.playerArena.setCardToBeMove(null);
            }

        }
    }

    public void summonSkillCard(FieldPos fieldPos) {
        if (this.getCardToBeMove() == null)
            return;
        if (!(this.getCardToBeMove() instanceof Skill))
            return;
        Skill card = (Skill) this.getCardToBeMove();
        int index = -1;
        // kalau card to be move di in hand
        try {
            index = this.player.useCard(card);
            int column = this.player.setSkillCardAtField(card, fieldPos);
            // TODO Hapus println
            System.out.println(this.player);
            if (this.playerArena != null) {
                if (card instanceof Destroy) {
                    this.playerArena.addThrowPlaceCard(this.playerArena.getCardToBeMove());
                } else {
                    this.playerArena.setSkillCardAtField(this.playerArena.getCardToBeMove(), column);
                    this.playerArena.getCardToBeMove().removeEventHandler(MouseEvent.MOUSE_CLICKED, cardOnClick);
                }
                this.playerArena.updatePowerCanUse(card.getElement(),
                        this.player.getPowerElementCanUse(card.getElement()));
            }
        } catch (PlaceCardException err) {
            this.player.insertCardInHand(index, card);
            errorHandler(err.getMessage());
        } catch (Exception err) {
            errorHandler(err.getMessage());
        } finally {
            this.player.setCardToBeMove(null);
            if (this.playerArena != null) {
                this.playerArena.setCardToBeMove(null);
            }

        }
    }

    public void errorHandler(String msg) {
        if (this.playerArena != null) {
            this.playerArena.showErrorAlert(msg);
        } else {
            System.out.println(msg);
        }
    }
}