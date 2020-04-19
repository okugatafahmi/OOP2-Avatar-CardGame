package com.avatarduel.gameplay;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.avatarduel.controller.PlayerController;
import com.avatarduel.controller.TestPlayerController;
import com.avatarduel.util.CSVReader;

import org.junit.Test;


public class TestGameplay {
    private Gameplay gameplay;
    private static final String CARD_CSV_FOLDER_PATH = "/com/avatarduel/card/data/";

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
    public void testGameplay() throws IOException, URISyntaxException {
        PlayerController[] playerControllers = TestPlayerController.makeControllers();
        Gameplay gameplay = new Gameplay(playerControllers);
        gameplay.loadCards();
        assertEquals(gameplay.listCard.size(), getTotalCard());
    }
}