package de.codechallenge.maikproba.rockpaperscissors.controllers.dtos;

import de.codechallenge.maikproba.rockpaperscissors.models.GameState;
import de.codechallenge.maikproba.rockpaperscissors.models.Player;
import de.codechallenge.maikproba.rockpaperscissors.models.Round;

import java.util.List;

public class GameStateDTO {
    public Player player1;

    public Player player2;

    public List<Round> rounds;

    public GameStateDTO(Player player1, Player player2, List<Round> rounds) {
        this.player1 = player1;
        this.player2 = player2;
        this.rounds = rounds;
    }

    public static class Converter {
        public static GameStateDTO toDTO(GameState gameState) {
            return new GameStateDTO(gameState.getPlayer1(), gameState.getPlayer2(), gameState.getRounds());
        }
    }
}
