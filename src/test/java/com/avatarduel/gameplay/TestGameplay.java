package com.avatarduel.gameplay;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.avatarduel.controller.TestPlayerController;
import com.avatarduel.util.CSVReader;

import org.junit.Test;


public class TestGameplay {
    private static final String CARD_CSV_FOLDER_PATH = "/com/avatarduel/card/data/";

    public static Gameplay makeGameplay() {
        return new Gameplay(TestPlayerController.makeControllers());
    }

    public int getTotalCard() throws IOException, URISyntaxException {
        int totalCard = 0;
        File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
        for (File file : folder.listFiles()) {
            CSVReader reader = new CSVReader(file, "\t");
            reader.setSkipHeader(true);
            List<String[]> rows = reader.read();
            totalCard += rows.size();
        }
        return totalCard;
    }

    @Test
    public void testLoadCard() throws IOException, URISyntaxException {
        Gameplay gameplay = TestGameplay.makeGameplay();
        gameplay.loadCards();
        assertEquals(gameplay.listCard.size(), getTotalCard());
    }

    @Test
    public void testSetDeck() throws IOException, URISyntaxException {
        Gameplay gameplay = TestGameplay.makeGameplay();
        gameplay.loadCards();
        for (int i=40; i<=60; ++i) {
            assertEquals(gameplay.setDeck(i).size(), i);
        }
    }
}