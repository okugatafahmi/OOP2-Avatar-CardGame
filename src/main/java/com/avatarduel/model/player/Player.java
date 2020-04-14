package com.avatarduel.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;
import java.util.Stack;

import com.avatarduel.exceptions.PowerElementNotEnough;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Element;

/**
 * Class yang bertanggung jawab dengan suatu karakter Player
 */
public class Player {
    private int health;
    private String name;
    private Stack<Card> deck;
    private ArrayList<Card> inHand;
    private HashMap<Element, Integer> powerTotal;
    private HashMap<Element, Integer> powerCanUse;
    private Character[] charactersField;
    private Skill[] skillField;
    private int totalDeckCard;

    public Player(String name, int totalDeckCard) {
        this.health = 80;
        this.name = name;
        this.inHand = new ArrayList<>();
        this.powerTotal = new HashMap<>();
        this.powerCanUse = new HashMap<>();
        this.charactersField = new Character[8];
        this.skillField = new Skill[8];
        this.totalDeckCard = totalDeckCard;
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
     * Mengembalikan total kartu deck
     * @return banyak kartu deck
     */
    public int getTotalDeckCard(){
        return this.totalDeckCard;
    }

    /**
     * Menambahkan element power player sesuai dengan element-nya
     * 
     * @param element element power to be added
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
     * 
     * @param card Card that be thrown
     * @throws PowerElementNotEnough if the player doesn't have enough power to throw that card
     * @throws NoSuchElementException if there isn't card at inHand
     */
    public void throwCard(Card card) throws PowerElementNotEnough, NoSuchElementException {
        int index = this.inHand.indexOf(card);
        if (index == -1) {
            throw new NoSuchElementException();
        } else {
            if (card.getClass().getName().equals("Land")) {
                addPower(card.getElement());
            } else {
                Integer freq = powerCanUse.get(card.getElement());
                if (freq > 0) {
                    powerCanUse.put(card.getElement(), freq-1);
                } else {
                    throw new PowerElementNotEnough(card.getElement());
                }
            }
        }
    }

    /**
     * Mengurangi health player sebanyak damage. Jika health kurang dari 0, health menjadi 0
     * 
     * @param damage the amount damage of the player got
     */
    public void getDamage(int damage) {
        int res = this.health - damage;
        this.health = ((res > 0) ? res : 0);
    }

    /**
     * Mengeset kartu character di field
     * 
     * @param card Skill card to be set
     * @param index Index of field to be set
     */
    public void setCharacterFieldAt(Character card, int index) {
        this.charactersField[index] = card;
    }

    /**
     * * Mengeset kartu field di field
     * 
     * @param card Character card to be set
     * @param index Index of field to be set
     */
    public void setSkillFieldAt(Skill card, int index) {
        this.skillField[index] = card;
    }
}