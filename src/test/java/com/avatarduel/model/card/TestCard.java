package com.avatarduel.model.card;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

public class TestCard {
    private static final String CARD_CSV_FOLDER_PATH = "/com/avatarduel/card/data/";

    @Test
    public void testCardFactory() throws URISyntaxException {
        File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());

        CardFactory cardFactory = new CardFactory();
        for (File file : folder.listFiles()) {
            String filename = file.getName().split("[.]")[0];
            Card card = cardFactory.getCard(filename);
            if (filename.equals("character")) {
                assertThat(card, instanceOf(Character.class));
            } else if (filename.equals("land")) {
                assertThat(card, instanceOf(Land.class));
            } else if (filename.equals("aura")) {
                assertThat(card, instanceOf(Aura.class));
            } else if (filename.equals("powerup")) {
                assertThat(card, instanceOf(PowerUp.class));
            } else if (filename.equals("destroy")) {
                assertThat(card, instanceOf(Destroy.class));
            }
        }
    }
}