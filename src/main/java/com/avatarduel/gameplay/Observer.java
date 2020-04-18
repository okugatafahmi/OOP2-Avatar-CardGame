package com.avatarduel.gameplay;

import com.avatarduel.model.player.DeckCardEmpty;

public interface Observer {
    /**
     * Do the update when there is new notify from subject (gameplay)
     * 
     * @throws DeckCardEmpty if the deck card empty when draw phase
     */
    public void update() throws DeckCardEmpty;

    /**
     * Set the subject (gameplay)
     * 
     * @param sub subject
     */
    public void setSubject(Subject sub);
}