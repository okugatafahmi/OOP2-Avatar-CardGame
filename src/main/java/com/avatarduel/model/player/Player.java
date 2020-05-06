package com.avatarduel.model.player;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.avatarduel.gameplay.GlobalField;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Destroy;
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
    private int hp;
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
    private Card cardToBeMove;
    private static final int TOTAL_CHARACTER_IN_FIELD = 6;
    private static final int TOTAL_SKILL_IN_FIELD = 6;
    public static final int N_COLUMN = 8;
    public static final int HP = 80;

    /**
     * Constructor. The name player will be set "XXXX" and total card deck is 40
     * 
     * @param id player id
     */
    public Player(int id) {
        this.id = id;
        this.hp = HP;
        this.name = "XXX";
        this.inHand = new LinkedList<>();
        this.powerTotal = new HashMap<>();
        this.powerCanUse = new HashMap<>();
        this.characterFields = new CharacterField[N_COLUMN];
        this.skillFields = new SkillField[N_COLUMN];
        this.totalDeckCard = 40;
        this.isUsedLand = false;
        this.totalCharacterInField = 0;
        this.totalSkillInField = 0;

        for (Element element : Element.values()) {
            powerTotal.put(element, 0);
            powerCanUse.put(element, 0);
        }

        for (int i = 0; i < N_COLUMN; ++i) {
            this.characterFields[i] = new CharacterField(id, i);
            this.skillFields[i] = new SkillField(id, i);
        }
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param totalDeckCard the totalDeckCard to set
     */
    public void setTotalDeckCard(int totalDeckCard) {
        this.totalDeckCard = totalDeckCard;
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
     * @param cardToBeMove the cardToBeMove to set
     */
    public void setCardToBeMove(Card cardToBeMove) {
        this.cardToBeMove = cardToBeMove;
    }

    /**
     * @return the cardToBeMove
     */
    public Card getCardToBeMove() {
        return cardToBeMove;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
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
     * Return total card in hand
     * 
     * @return total card in hand
     */
    public int getTotalCardInHand() {
        return this.inHand.size();
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
     * @return the totalCharacterInField
     */
    public int getTotalCharacterInField() {
        return totalCharacterInField;
    }

    /**
     * Menambahkan element power player sesuai dengan element-nya
     * 
     * @param element element power to be added
     */
    public void addPower(Element element) {
        Integer freqTotal = this.powerTotal.get(element);
        Integer freqCanUse = this.powerCanUse.get(element);
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
     * 
     * @throws DeckCardEmpty if the deck is empty
     */
    public void setupDrawPhase() throws DeckCardEmpty {
        powerCanUse.putAll(powerTotal);
        for (CharacterField characterField : characterFields) {
            characterField.setHasAttacked(false);
            characterField.setFirstSummon(false);
        }
        isUsedLand = false;
        if (this.getTotalCardInDeck() == 0) {
            throw new DeckCardEmpty(id);
        }
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
     * Reduce player's hp based the amount of damage. If
     * {@code damage > hp}, hp will be 0
     * 
     * @param damage the amount damage of the player got
     */
    public void getDamage(int damage) {
        int res = this.hp - damage;
        this.hp = ((res > 0) ? res : 0);
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
     * Get character field at spesifed column
     * 
     * @param column character field's column
     * @return the field
     */
    public CharacterField getCharacterField(int column) {
        return this.characterFields[column];
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
        if (!(card instanceof Destroy)) {
            ++totalSkillInField;
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

    /**
     * Change character field's stance at spesified column
     * 
     * @param column field's column
     */
    public void changeStance(int column) {
        this.characterFields[column].changeStance();
    }

    /**
     * Attach skill to spesfied character field's column
     * 
     * @param skillPos skill position
     * @param column   character field's column
     */
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
                + "\n" + name + " " + hp + " " + getTotalCardInDeck();
        return res;
    }
}