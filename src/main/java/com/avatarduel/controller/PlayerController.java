package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.view.PlayerArena;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import com.avatarduel.gameplay.GameState;
import com.avatarduel.gameplay.Observer;
import com.avatarduel.gameplay.Phase;
import com.avatarduel.gameplay.Subject;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.player.Player;


/**
 * Class that responsible as controller of model (player) and view (playerArena)
 */
public class PlayerController implements Observer {
    private Player player;
    private PlayerArena playerArena;
    private Subject gameplay;
    private int id;

    public PlayerController(Player player, int id, boolean isMirror){
        this.player = player;
        this.id = id;
        this.playerArena = new PlayerArena(isMirror);
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
        if (gameState.getTurn() != this.id) {   // turn lawan
            if (gameState.getPhase() == Phase.DRAW) {
                this.playerArena.setFaceCardInHand(false);
            }
        }
        else {
            if (gameState.getPhase() == Phase.DRAW) {
                this.playerArena.setFaceCardInHand(true);
            }
        }
    }

    /**
     * Return the view of player arena
     * @return view of player arena
     */
    public PlayerArena getPlayerArena(){
        return playerArena;
    }

    /**
     * Set the player deck
     * @param deck stack of card that represent the deck
     */
    public void setDeck(Stack<Card> deck){
        this.player.setDeck(deck);
        this.playerArena.setDeck(deck);
        this.playerArena.setDeckClickHandler(new EventHandler<MouseEvent>(){
        
            @Override
            public void handle(MouseEvent event) {
                if (gameplay.getUpdate().getTurn() == id) {
                    if (gameplay.getUpdate().getPhase() == Phase.DRAW) {
                        drawCard();
                        gameplay.update();
                    }
                }
            }
        });
    }

    /**
     * Set mouse over event on all card in deck. The card will be hover in hoverSpace
     * @param hoverSpace space for the card hover
     */
    public void setDeckCardHover(StackPane hoverSpace) {
        this.playerArena.setDeckCardHover(hoverSpace);
    }

    public int getTotalDeckCard(){
        return this.player.getTotalDeckCard();
    }

    /**
     * Prosedure that draw 7 card in the first game
     */
    public void firstDrawCard(){
        for (int i=0; i<7; ++i){
            this.drawCard();
        }
    }

    /**
     * Procedure that draw a card from deck
     */
    public void drawCard(){
        this.player.drawCard();
        this.playerArena.drawCard();
    }
}