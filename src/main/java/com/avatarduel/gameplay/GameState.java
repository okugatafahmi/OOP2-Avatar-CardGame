package com.avatarduel.gameplay;

/**
 * Class that responsible for the game state
 */
public class GameState {
    private Phase phase;
    private int turn;

    /**
     * Default constructor. Set the attribute phase to be Phase.Draw and turn to be
     * 0
     */
    public GameState() {
        this.phase = Phase.DRAW;
        this.turn = 0;
    }

    public GameState(Phase phase, int turn) {
        this.phase = phase;
        this.turn = turn;
    }

    /**
     * Next game state
     */
    public void next() {
        if (phase.equals(Phase.DRAW)) {
            phase = Phase.MAIN;
        } else if (phase.equals(Phase.MAIN)) {
            phase = Phase.BATTLE;
        } else if (phase.equals(Phase.BATTLE)) {
            phase = Phase.END;
        } else { // Phase.END
            phase = Phase.DRAW;
            turn = (turn + 1) % 2;
        }
    }

    /**
     * @return the phase
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Return true if game state equal with phase and turn
     * 
     * @param phase
     * @param turn
     * @return
     */
    public boolean equals(Phase phase, int turn) {
        return this.phase == phase && this.turn == turn;
    }

    @Override
    public String toString() {
        return "Phase: " + phase.toString() + "\n" + "Player: " + (turn + 1);
    }
}