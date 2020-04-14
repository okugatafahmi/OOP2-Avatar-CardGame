package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.view.CardView;
import com.avatarduel.view.PlayerArena;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.player.Player;


/**
 * Class that responsible as controller of model (player) and view (playerArena)
 */
public class PlayerController {
    private Player player;
    private PlayerArena playerArena;

    public PlayerController(Player player, boolean isMirror){
        this.player = player;
        this.playerArena = new PlayerArena(isMirror);
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
        Card card = this.player.drawCard();
        this.playerArena.addInHand(new CardView(card));
    }
}