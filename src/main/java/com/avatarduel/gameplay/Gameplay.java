package com.avatarduel.gameplay;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.avatarduel.model.card.Element;
import com.avatarduel.controller.PlayerController;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.CardBuilder;
import com.avatarduel.util.CSVReader;

/**
 * Kelas Gameplay yang bertanggung jawab dalam permainan
 */
public class Gameplay {
    private static final String CARD_CSV_FOLDER_PATH = "../card/data/";
    private int[] totalCardDeck;
    private LinkedList<Card> listCard;
    private PlayerController[] playerControllers;

    public Gameplay(int[] totalCardDeck){
        this.totalCardDeck = totalCardDeck;
    }

    /**
     * Meload kartu
     * @throws IOException
     * @throws URISyntaxException
     */
    public void loadCards() throws IOException, URISyntaxException {
        File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
        System.out.println("tes");
        LinkedList<Card> loaded = new LinkedList<>();
        for (File file : folder.listFiles()) {
            String filename = file.getName().split("[.]")[0];
            CSVReader landReader = new CSVReader(file, "\t");
            landReader.setSkipHeader(true);
            List<String[]> landRows = landReader.read();
            for (String[] row : landRows) {
            CardBuilder builder = new CardBuilder(filename)
                                        .name(row[1])
                                        .element(Element.valueOf(row[2]))
                                        .description(row[3])
                                        .image(row[4]);

            if (filename.equals("character") || filename.equals("skill")) {
                builder = builder.power(Integer.parseInt(row[5]))
                                .attack(Integer.parseInt(row[6]))
                                .defense(Integer.parseInt(row[7]));
                if (filename.equals("skill")) {
                    builder = builder.effect(row[8]);
                }
            }
                loaded.add(builder.build());
            }
        }
        listCard = loaded;
    }

    /**
     * Menambahkan controller pemain
     * @param playerControllers
     */
    public void addPlayers(PlayerController[] playerControllers){
        this.playerControllers = playerControllers;
    }

    /**
     * Mengembalikan deck pemain
     * @return
     */
    public Stack<Card> setDeck(int nCard){
        Stack<Card> deck = new Stack<>();
        return deck;
    }

    public void run(){
        this.playerControllers[0].setDeck(setDeck(this.totalCardDeck[0]));
        this.playerControllers[1].setDeck(setDeck(this.totalCardDeck[1]));
    }
}