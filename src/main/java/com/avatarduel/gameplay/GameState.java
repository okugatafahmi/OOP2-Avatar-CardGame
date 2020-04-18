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
        this.phase = Phase.READY;
        this.turn = 0;
    }

    /**
     * Finish the game
     * 
     * @param winner winner id
     */
    public void setFinish(int winner) {
        this.phase = Phase.FINISHED;
        this.turn = winner;
    }

    /**
     * Next game state
     */
    public void next() {
        if (phase == Phase.READY) {
            phase = Phase.DRAW;
        }
        else if (phase == Phase.DRAW) {
            phase = Phase.MAIN;
        } else if (phase == Phase.MAIN) {
            phase = Phase.BATTLE;
        } else if (phase == Phase.BATTLE) {
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
     * @param phase the phase
     * @param turn  the turn
     * @return return {@code true} if current game state is equal with phase and
     *         turn
     */
    public boolean equals(Phase phase, int turn) {
        return this.phase == phase && this.turn == turn;
    }

    @Override
    public String toString() {
        if (phase == Phase.READY) {
            return "Avatar Duel!!";
        } else if (phase == Phase.FINISHED) {
            return "Finished!!";
        }
        return "Phase: " + phase.toString() + "\n" + "Player: " + (turn + 1);
    }
}