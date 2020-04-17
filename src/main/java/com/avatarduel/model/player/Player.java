package com.avatarduel.model.player;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.avatarduel.gameplay.GlobalField;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.CardInFieldExist;
import com.avatarduel.model.field.FieldPos;
import com.avatarduel.model.field.NoneCharacterCard;
import com.avatarduel.model.field.CharacterField;
import com.avatarduel.model.field.SkillField;
import com.avatarduel.model.card.Element;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;

/**
 * Class yang bertanggung jawab dengan suatu karakter Player
 */
public class Player {
    private int id;
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

    public Player(int id, String name, int totalDeckCard) {
        this.id = id;
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
            this.characterFields[i] = new CharacterField(id, i);
            this.skillFields[i] = new SkillField(id, i);
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the totalDeckCard
     */
    public int getTotalDeckCard() {
        return totalDeckCard;
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
    public void setupDrawPhase() {
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
     */
    public int useCard(Card card) throws PowerElementNotEnough, NoSuchElementException, HasUsedLand {
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
                if (freq < ((Summonedable) card).getPower()) {
                    throw new PowerElementNotEnough(card.getElement());
                }
                powerCanUse.put(card.getElement(), freq - ((Summonedable) card).getPower());
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
     * Set global field
     * 
     * @param globalField global field
     */
    public void setGlobalField(GlobalField globalField) {
        for (CharacterField characterField : characterFields) {
            characterField.setGlobalField(globalField);
        }
        for (SkillField skillField : skillFields) {
            skillField.setGlobalField(globalField);
        }
    }

    /**
     * Set character card on field
     * 
     * @param card   card to set
     * @param column column of field to set
     * @throws CardInFieldExist   if there is card on field
     * @throws MaximumCardInField if cards summoned in field have reached maximum
     *                            number
     */
    public void setCharacterCardAtField(Character card, int column) throws CardInFieldExist, MaximumCardInField {
        if (totalCharacterInField == TOTAL_CHARACTER_IN_FIELD) {
            throw new MaximumCardInField("Character", TOTAL_CHARACTER_IN_FIELD);
        }
        this.characterFields[column].setCard(card);
        ++totalCharacterInField;
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
        Summonedable ret = this.characterFields[column].removeCard();
        if (ret != null) {
            --totalCharacterInField;
        }
        return ret;
    }

    /**
     * * Set skill card on field
     * 
     * @param card     card to set
     * @param attachTo card position where skill card attach to
     * @return column where the card set
     * @throws CardInFieldExist   if there is card on field
     * @throws MaximumCardInField if cards summoned in field have reached maximum
     *                            number
     * @throws NoneCharacterCard  if the selected character field card is empty
     */
    public int setSkillCardAtField(Skill card, FieldPos attachTo)
            throws CardInFieldExist, MaximumCardInField, NoneCharacterCard {
        if (totalSkillInField == TOTAL_SKILL_IN_FIELD) {
            throw new MaximumCardInField("Skill", TOTAL_CHARACTER_IN_FIELD);
        }
        int column = attachTo.getColumn();
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
                        throw new MaximumCardInField("Skill", column);
                    }
                }
            }
        }
        ++totalSkillInField;
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
        Summonedable ret = this.skillFields[column].removeCard();
        if (ret != null) {
            --this.totalSkillInField;
        }
        return ret;
    }

    /**
     * Remove character's skill
     * 
     * @param skillPos skill pos
     * @param column   column of character
     */
    public void removeSkillOfCharacterAtField(FieldPos skillPos, int column) {
        this.characterFields[column].removeSkill(skillPos);
    }

    public void changeStance(int column) {
        this.characterFields[column].changeStance();
    }

    public void attachSkill(FieldPos skillPos, int column) {
        this.characterFields[column].attachSkill(skillPos);
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < N_COLUMN; ++i) {
            res += "Character " + i + " = " + characterFields[i].toString() + "\n" + "Skill " + i + " = "
                    + skillFields[i].toString() + "\n";
        }
        res += inHand + "\n" + powerCanUse + "/ " + powerTotal + "\n" + totalCharacterInField + " " + totalSkillInField
                + "\n" + health;
        return res;
    }
}