package de.codechallenge.maikproba.rockpaperscissors;

import de.codechallenge.maikproba.rockpaperscissors.models.GameState;
import de.codechallenge.maikproba.rockpaperscissors.models.HandSign;
import de.codechallenge.maikproba.rockpaperscissors.models.Player;
import de.codechallenge.maikproba.rockpaperscissors.services.GameStateManager;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameStateManagerTest {
    @Autowired
    private GameStateManager gameStateManager;

    @Test
    public void testChooseWinnerOfRoundRockCrushesScissorsPlayer1Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.ROCK);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer1());
    }

    @Test
    public void testChooseWinnerOfRoundRockCrushesScissorsPlayer2Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.ROCK);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.SCISSORS);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer2());
    }

    @Test
    public void testChooseWinnerOfRoundScissorsCutsPaperPlayer1Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.SCISSORS);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.PAPER);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer1());
    }

    @Test
    public void testChooseWinnerOfRoundScissorsCutsPaperPlayer2Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer2());
    }

    @Test
    public void testChooseWinnerOfRoundPaperCoversRockPlayer1Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.ROCK);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer1());
    }

    @Test
    public void testChooseWinnerOfRoundPaperCoversRockPlayer2Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.ROCK);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNotNull();
        assertThat(winnerOfRound1).isEqualTo(gameState.getPlayer2());
    }

    @Test
    public void testChooseWinnerOfRoundShouldBeTie() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);
        int round = 1;

        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);

        // Act
        Player winnerOfRound1 = this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Assert
        assertThat(winnerOfRound1).isNull();
    }

    @Test
    public void testChooseGameWinnerPlayer1Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);

        int round = 1;
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.ROCK);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 2;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.ROCK);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.ROCK);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 3;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.ROCK);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Act
        Player winnerOfGame = this.gameStateManager.chooseGameWinner(gameUUID);

        // Assert
        assertThat(winnerOfGame).isNotNull();
        assertThat(winnerOfGame).isEqualTo(gameState.getPlayer1());
    }

    @Test
    public void testChooseGameWinnerPlayer2Wins() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);

        int round = 1;
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 2;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.SCISSORS);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 3;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.SCISSORS);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.ROCK);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Act
        Player winnerOfGame = this.gameStateManager.chooseGameWinner(gameUUID);

        // Assert
        assertThat(winnerOfGame).isNotNull();
        assertThat(winnerOfGame).isEqualTo(gameState.getPlayer2());
    }

    @Test
    public void testChooseGameWinnerItsATie() {
        // Arrange
        String gameUUID = this.gameStateManager.createNewGame();
        GameState gameState = this.gameStateManager.getGameStateByUUID(gameUUID);

        int round = 1;
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 2;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.ROCK);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.SCISSORS);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        round = 3;
        this.gameStateManager.createNewRoundInGameByUUID(gameUUID);
        gameState.getRounds().get(round-1).setHandSignPlayer1(HandSign.PAPER);
        gameState.getRounds().get(round-1).setHandSignPlayer2(HandSign.PAPER);
        this.gameStateManager.chooseWinnerOfRound(gameUUID, round);

        // Act
        Player winnerOfGame = this.gameStateManager.chooseGameWinner(gameUUID);

        // Assert
        assertThat(winnerOfGame).isNull();
    }
}
