package com.avatarduel.gameplay;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.Element;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;
import com.avatarduel.controller.PlayerController;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.CardFactory;
import com.avatarduel.model.card.Character;
import com.avatarduel.util.CSVReader;

/**
 * Kelas Gameplay yang bertanggung jawab dalam permainan
 */
public class Gameplay implements Subject {
    private static final String CARD_CSV_FOLDER_PATH = "../card/data/";
    private LinkedList<Card> listCard;
    private PlayerController[] playerControllers;
    private GameState gameState;

    public Gameplay(PlayerController[] playerControllers) {
        this.playerControllers = playerControllers;
    }

    /**
     * Load cards
     * 
     * @throws IOException        if the file is not found
     * @throws URISyntaxException if this URL is not formatted strictly according to
     *                            to RFC2396 and cannot be converted to a URI.
     */
    public void loadCards() throws IOException, URISyntaxException {
        File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
        LinkedList<Card> loaded = new LinkedList<>();
        CardFactory cardFactory = new CardFactory();
        for (File file : folder.listFiles()) {
            String filename = file.getName().split("[.]")[0];
            CSVReader reader = new CSVReader(file, "\t");
            reader.setSkipHeader(true);
            List<String[]> rows = reader.read();
            for (String[] row : rows) {
                Card card = cardFactory.getCard(filename);
                card.setName(row[1]);
                card.setElement(Element.valueOf(row[2]));
                card.setDescription(row[3]);
                card.setImagePath(getClass().getResource(row[4]).toString());

                if (filename.equals("character")) {
                    ((Character) card).setPower(Integer.parseInt(row[5]));
                    ((Character) card).setAttack(Integer.parseInt(row[6]));
                    ((Character) card).setDefense(Integer.parseInt(row[7]));
                } else if (filename.equals("skill")) {
                    ((Skill) card).setPower(Integer.parseInt(row[5]));
                    ((Skill) card).setAttack(Integer.parseInt(row[6]));
                    ((Skill) card).setDefense(Integer.parseInt(row[7]));
                    ((Skill) card).setEffect(row[8]);
                }
                loaded.add(card);
            }
        }
        listCard = loaded;
    }

    /**
     * Return player's deck
     * 
     * @param nCard maximal card in deck
     * @return stack of card which represent the deck
     */
    public Stack<Card> setDeck(int nCard) {
        Stack<Card> deck = new Stack<>();
        List<Card> listCard;
        listCard = this.listCard.stream().filter(card -> card instanceof Character).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, nCard * 2 / 5).stream().forEach(card -> deck.push(card));
        listCard = this.listCard.stream().filter(card -> card instanceof Land).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, nCard * 2 / 5).stream().forEach(card -> deck.push(card));
        listCard = this.listCard.stream().filter(card -> card instanceof Skill).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, nCard / 5).stream().forEach(card -> deck.push(card));
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Run the game
     */
    public void run() {
        for (int i = 0; i < 2; ++i) {
            playerControllers[i].setSubject(this);
            playerControllers[i].setDeck(this.setDeck(playerControllers[i].getTotalDeckCard()));
            playerControllers[i].firstDrawCard();
        }
        this.gameState = new GameState();
        notifyObserver();
    }

    @Override
    public GameState getUpdate() {
        return this.gameState;
    }

    @Override
    public void notifyObserver(){
        for (Observer observer : this.playerControllers) {
            observer.update();
        }
    }
}