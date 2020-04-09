package com.avatarduel.controller;

import java.util.Stack;

import com.avatarduel.gui.PlayerArena;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.player.Player;


/**
 * Kelas yang bertanggung jawab untuk control player
 */
public class PlayerController {
    private Player player;
    private PlayerArena playerArena;

    public PlayerController(Player player, boolean isMirror){
        this.player = player;
        this.playerArena = new PlayerArena(isMirror);
    }

    public PlayerArena getPlayerArena(){
        return playerArena;
    }

    public void setDeck(Stack<Card> deck){
        this.player.setDeck(deck);
    }
}