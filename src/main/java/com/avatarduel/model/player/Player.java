package com.avatarduel.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;
import java.util.Stack;

import com.avatarduel.exceptions.PowerElementNotEnough;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.enums.Element;

/**
 * Class yang bertanggung jawab dengan suatu karakter Player
 */
public class Player {
    private int health;
    private String name;
    private Stack<Card> deck;
    private List<Card> inHand;
    private Map<Element, Integer> powerTotal;
    private Map<Element, Integer> powerCanUse;

    public Player(String name) {
        this.health = 80;
        this.name = name;
        this.inHand = new ArrayList<>();
        this.powerTotal = new HashMap<>();
        this.powerCanUse = new HashMap<>();
    }

    public void setDeck(Stack<Card> deck) {
        this.deck = deck;
    }

    /**
     * Mengembalikan health player
     * 
     * @return health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Mengembalikan nama player
     * 
     * @return player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Menambahkan element power player sesuai dengan element-nya
     * 
     * @param element
     */
    public void addPower(Element element) {
        Integer freqTotal = this.powerTotal.get(element);
        Integer freqCanUse = this.powerTotal.get(element);
        if (freqTotal == null) {
            this.powerTotal.put(element, 1);
            this.powerCanUse.put(element, 1);
        } else {
            this.powerTotal.put(element, freqTotal + 1);
            this.powerCanUse.put(element, freqCanUse + 1);
        }
    }

    /**
     * Mengambil 1 kartu dari deck dan di simpan di inHand. Di panggil ketika draw
     * phase player
     * 
     * @return card yang diambil dari deck
     * @throws EmptyStackException jika deck kosong
     */
    public Card drawCard() throws EmptyStackException {
        Card card = deck.pop();
        inHand.add(card);
        powerCanUse.putAll(powerTotal);
        return card;
    }

    /**
     * Procedure ketika pemain mengeluarkan suatu kartu
     * @param card
     * @throws PowerElementNotEnough
     * @throws NoSuchElementException
     */
    public void throwCard(Card card) throws PowerElementNotEnough, NoSuchElementException {
        int index = this.inHand.indexOf(card);
        if (index == -1) {
            throw new NoSuchElementException();
        } else {
            if (card.getClass().getName().equals("Land")) {
                // addPower(card.getElement());
            } else {
                Integer freq = 0; // powerCanUse.get(card.getElement());
                if (freq > 0){
                    // powerCanUse.put(card.getElement(), freq-1);
                }
                else{
                    // throw new PowerElementNotEnough(card.getElement());
                }
            }
        }
    }

    /**
     * Mengurangi health player sebanyak damage. Jika health < 0, health menjadi 0
     * 
     * @param damage
     */
    public void getDamage(int damage) {
        int res = this.health - damage;
        this.health = ((res > 0) ? res : 0);
    }
}