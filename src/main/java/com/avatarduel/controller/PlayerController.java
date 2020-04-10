package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.gui.PlayerArena;
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
}