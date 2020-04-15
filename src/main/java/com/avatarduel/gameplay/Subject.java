package com.avatarduel.gameplay;

public interface Subject {
    /**
     * Notify all the observer
     */
    public void notifyObserver();

    /**
     * Get current game state
     * @return game state
     */
    public GameState getUpdate();

    /**
     * Update game state
     */
    public void update();
}