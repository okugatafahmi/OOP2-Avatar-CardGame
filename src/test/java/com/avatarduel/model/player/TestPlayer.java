package com.avatarduel.model.player;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import com.avatarduel.gameplay.Gameplay;
import com.avatarduel.gameplay.TestGameplay;
import com.avatarduel.model.card.Element;

import org.junit.Test;

public class TestPlayer {
    public static Player[] makePlayers() {
        Player[] players = new Player[2];
        for (int i = 0; i < 2; ++i) {
            players[i] = new Player(i);
        }
        return players;
    }

    @Test
    public void testPower() {
        Player[] players = makePlayers();
        for (int i = 0; i < 1; ++i) {
            for (Element element : Element.values()) {
                assertEquals(players[i].getPowerElementCanUse(element), 0);
                assertEquals(players[i].getPowerElementTotal(element), 0);
            }
        }
        players[0].addPower(Element.EARTH);
        for (Element element : Element.values()) {
            if (element == Element.EARTH) {
                assertEquals(players[0].getPowerElementCanUse(Element.EARTH), 1);
                assertEquals(players[0].getPowerElementTotal(Element.EARTH), 1);
            } else {
                assertEquals(players[0].getPowerElementCanUse(element), 0);
                assertEquals(players[0].getPowerElementTotal(element), 0);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void testPlayerTotalCardInDeckFirst() {
        Player player = new Player(0);
        player.getTotalCardInDeck();
    }

    @Test
    public void testCoba() throws IOException, URISyntaxException {
        Gameplay gameplay = TestGameplay.makeGameplay();
        gameplay.loadCards();
        Player player = new Player(0);
        player.setTotalDeckCard(60);
        player.setDeck(gameplay.setDeck(60));
        assertEquals(player.getTotalCardInDeck(), 60);
    }

}