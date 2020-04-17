package com.avatarduel.model.player;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.CardInFieldExist;
import com.avatarduel.model.field.CardPos;
import com.avatarduel.model.field.CharacterField;
import com.avatarduel.model.field.SkillField;
import com.avatarduel.model.card.Element;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;

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
    private CharacterField[] characterFields;
    private SkillField[] skillFields;
    private int totalDeckCard;
    private boolean isUsedLand;
    private int totalCharacterInField;
    private int totalSkillInField;
    private static final int TOTAL_CHARACTER_IN_FIELD = 6;
    private static final int TOTAL_SKILL_IN_FIELD = 6;
    public static final int N_COLUMN = 8;

    public Player(String name, int totalDeckCard) {
        this.health = 80;
        this.name = name;
        this.inHand = new LinkedList<>();
        this.powerTotal = new HashMap<>();
        this.powerCanUse = new HashMap<>();
        this.characterFields = new CharacterField[N_COLUMN];
        this.skillFields = new SkillField[N_COLUMN];
        this.totalDeckCard = totalDeckCard;
        this.isUsedLand = false;
        this.totalCharacterInField = 0;
        this.totalSkillInField = 0;

        for (Element element : Element.values()) {
            // TODO kembaliin 0
            powerTotal.put(element, 99);
            powerCanUse.put(element, 0);
        }

        for (int i = 0; i < N_COLUMN; ++i) {
            this.characterFields[i] = new CharacterField();
            this.skillFields[i] = new SkillField();
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
     * @return index card to be used
     * @throws PowerElementNotEnough  if the player doesn't have enough power to use
     *                                that card
     * @throws NoSuchElementException if there isn't card at inHand
     * @throws HasUsedLand            if player has used land card
     * @throws MaximumCardInField     if cards summoned in field have reached
     *                                maximum number
     */
    public int useCard(Card card)
            throws PowerElementNotEnough, NoSuchElementException, HasUsedLand, MaximumCardInField {
        int index = this.inHand.indexOf(card);
        if (index == -1) {
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
                if (card instanceof Character) {
                    if (totalCharacterInField == TOTAL_CHARACTER_IN_FIELD) {
                        throw new MaximumCardInField("Character", TOTAL_CHARACTER_IN_FIELD);
                    }
                    ++totalCharacterInField;
                } else if (card instanceof Skill) {
                    if (totalSkillInField == TOTAL_SKILL_IN_FIELD) {
                        throw new MaximumCardInField("Skill", TOTAL_CHARACTER_IN_FIELD);
                    }
                    ++totalSkillInField;
                }
                powerCanUse.put(card.getElement(), freq - 1);
            }
            removeCardInHand(card);
        }
        return index;
    }

    /**
     * Insert card in hand with specified index
     * 
     * @param index index inHand
     * @param card  card to be inserted
     */
    public void insertCardInHand(int index, Card card) {
        this.inHand.add(index, card);
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
     * Set character card on field
     * 
     * @param card   card to set
     * @param column column of field to set
     * @throws CardInFieldExist if there is card on field
     */
    public void setCharacterCardAtField(Character card, int column) throws CardInFieldExist {
        this.characterFields[column].setCard(card);
    }

    /**
     * Get character card on spesified field column
     * 
     * @param column field's column
     * @return card on field. {@code null} if there is no card
     */
    public Summonedable getCharacterCardAtField(int column) {
        return this.characterFields[column].getCard();
    }

    /**
     * Remove character card on specified field column
     * 
     * @param column field's column
     * @return card in this field. 'null' if there is none card in field
     */
    public Summonedable removeCharacterCardAtField(int column) {
        return this.characterFields[column].removeCard();
    }

    /**
     * * Set skill card on field
     * 
     * @param card     card to set
     * @param column   column of field to set
     * @param attachTo card position where skill card attach to
     * @return column where the card set
     * @throws CardInFieldExist if there is card on field
     */
    public int setSkillCardAtField(Skill card, int column, CardPos attachTo) throws CardInFieldExist {
        try {
            this.skillFields[column].setCard(card, attachTo);
        } catch (CardInFieldExist e) {
            boolean isSuccess = false;
            int columnOld = column;
            column = (column + 1) % N_COLUMN;
            while (column != columnOld && !isSuccess) {
                try {
                    this.skillFields[column].setCard(card, attachTo);
                    isSuccess = true;
                } catch (CardInFieldExist err) {
                    column = (column + 1) % N_COLUMN;
                    if (column == columnOld) {
                        throw err;
                    }
                }
            }
        }
        return column;
    }

    /**
     * Get skill card on spesified field column
     * 
     * @param column field's column
     * @return card on field. {@code null} if there is no card
     */
    public Summonedable getSkillCardAtField(int column) {
        return this.skillFields[column].getCard();
    }

    /**
     * Remove skill card on specified field column
     * 
     * @param column field's column
     * @return card in this field. 'null' if there is none card in field
     */
    public Summonedable removeSkillCardAtField(int column) {
        return this.skillFields[column].removeCard();
    }

    public void changeStance(int column) {
        this.characterFields[column].changeStance();
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < N_COLUMN; ++i) {
            res += "Character " + i + " = " + characterFields[i].toString() + "\n" + "Skill " + i + " = "
                    + skillFields[i].toString() + "\n";
        }
        return res + inHand;
    }
}