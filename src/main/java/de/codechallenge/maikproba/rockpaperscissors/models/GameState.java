package de.codechallenge.maikproba.rockpaperscissors.models;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private Player player1;

    private Player player2;

    private List<Round> rounds;

    public GameState(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.rounds = new ArrayList<>();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
