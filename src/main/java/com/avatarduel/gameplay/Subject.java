package com.avatarduel.gameplay;

public interface Subject {
    /**
     * Notify all the observer
     */
    public void notifyObserver();

    /**
     * Get current gamestate
     * @return game state
     */
    public GameState getUpdate();
}