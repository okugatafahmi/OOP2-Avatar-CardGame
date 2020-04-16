package com.avatarduel.model.player;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.CardInFieldExist;
import com.avatarduel.model.field.ColumnField;
import com.avatarduel.model.field.NoneCharacterCard;
import com.avatarduel.model.card.Element;
import com.avatarduel.model.card.Land;

/**
 * Class yang bertanggung jawab dengan suatu karakter Player
 */
public class Player {
    private int health;
    private String name;
    private Stack<Card> deck;
    private LinkedList<Card> inHand;
    private HashMap<Element, Integer> powerTotal;
    private HashMap<Element, Integer> powerCanUse;
    private ColumnField[] columnField;
    private int totalDeckCard;
    private boolean isUsedLand;

    public Player(String name, int totalDeckCard) {
        this.health = 80;
        this.name = name;
        this.inHand = new LinkedList<>();
        this.powerTotal = new HashMap<>();
        this.powerCanUse = new HashMap<>();
        this.columnField = new ColumnField[8];
        this.totalDeckCard = totalDeckCard;
        this.isUsedLand = false;

        for (Element element : Element.values()) {
            powerTotal.put(element, 99);
            powerCanUse.put(element, 0);
        }

        for (int i = 0; i < 8; ++i) {
            this.columnField[i] = new ColumnField();
        }
    }

    /**
     * Set player's deck
     * 
     * @param deck card deck
     */
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
     * 
     * @return banyak kartu deck
     */
    public int getTotalDeckCard() {
        return this.totalDeckCard;
    }

    /**
     * Return total card in deck now
     * 
     * @return total card in deck now
     */
    public int getTotalCardInDeck() {
        return this.deck.size();
    }

    /**
     * Get player's power total element
     * 
     * @param element element card
     * @return total power
     */
    public int getPowerElementTotal(Element element) {
        return this.powerTotal.get(element);
    }

    /**
     * Get player's power total element can be used
     * 
     * @param element element card
     * @return total power can be used
     */
    public int getPowerElementCanUse(Element element) {
        return this.powerCanUse.get(element);
    }

    /**
     * Menambahkan element power player sesuai dengan element-nya
     * 
     * @param element element power to be added
     */
    public void addPower(Element element) {
        Integer freqTotal = this.powerTotal.get(element);
        Integer freqCanUse = this.powerTotal.get(element);
        this.powerTotal.put(element, freqTotal + 1);
        this.powerCanUse.put(element, freqCanUse + 1);
    }

    /**
     * Return true if card is in hand
     * 
     * @param card card to be search
     * @return return {@code true} if card in hand
     */
    public boolean isCardInHand(Card card) {
        return this.inHand.contains(card);
    }

    /**
     * Mengambil 1 kartu dari deck dan di simpan di inHand. Di panggil ketika draw
     * phase player
     */
    public void drawCard() {
        Card card = deck.pop();
        inHand.add(card);
    }

    /**
     * Reset some attribute when it is player's draw phase
     */
    public void setUpDrawPhase() {
        powerCanUse.putAll(powerTotal);
        isUsedLand = false;
    }

    /**
     * Procedure ketika pemain mengeluarkan suatu kartu
     * 
     * @param card Card that be thrown
     * @throws PowerElementNotEnough  if the player doesn't have enough power to use
     *                                that card
     * @throws NoSuchElementException if there isn't card at inHand
     * @throws HasUsedLand            if player has used land card
     */
    public void useCard(Card card) throws PowerElementNotEnough, NoSuchElementException, HasUsedLand {
        if (!this.inHand.contains(card)) {
            throw new NoSuchElementException("There is no card " + card.getName());
        } else {
            if (card instanceof Land) {
                if (isUsedLand) {
                    throw new HasUsedLand();
                }
                addPower(card.getElement());
                isUsedLand = true;
            } else {
                Integer freq = powerCanUse.get(card.getElement());
                if (freq == 0) {
                    throw new PowerElementNotEnough(card.getElement());
                }
                powerCanUse.put(card.getElement(), freq - 1);
            }
            removeCardInHand(card);
        }
    }

    /**
     * Remove card in hand
     * 
     * @param card card will be remove
     */
    public void removeCardInHand(Card card) {
        // TODO hapus println jika sudah tidak dipakai. Hanya utk debug
        System.out.println(inHand);
        this.inHand.remove(card);
        System.out.println(inHand);
    }

    /**
     * Mengurangi health player sebanyak damage. Jika health kurang dari 0, health
     * menjadi 0
     * 
     * @param damage the amount damage of the player got
     */
    public void getDamage(int damage) {
        int res = this.health - damage;
        this.health = ((res > 0) ? res : 0);
    }

    /**
     * Set card on field
     * 
     * @param card   card to set
     * @param column column of field to set
     * @throws CardInFieldExist  if there is card on field
     * @throws NoneCharacterCard if use skill card to none character card on field
     */
    public void setCardAtField(Summonedable card, int column) throws CardInFieldExist, NoneCharacterCard {
        this.columnField[column].setCardField(card);
    }

    /**
     * Remove character card on specified field column
     * 
     * @param column field's column
     */
    public void removeCharacterCardAtField(int column) {
        this.columnField[column].removeCharacterCard();
    }

    /**
     * Remove character card on specified field column
     * 
     * @param column field's column
     */
    public void removeSkillCardAtField(int column) {
        this.columnField[column].removeSkillCard();
    }

    public void changeStance(int column) {
        this.columnField[column].changeStance();
    }

    @Override
    public String toString() {
        String res = "";
        int i = 0;
        for (ColumnField column : columnField) {
            res += "Kolom " + i + column.toString() + "\n";
            ++i;
        }
        return res + inHand;
    }
}