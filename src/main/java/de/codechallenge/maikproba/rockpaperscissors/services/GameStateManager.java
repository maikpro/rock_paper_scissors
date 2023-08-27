package de.codechallenge.maikproba.rockpaperscissors.services;


import de.codechallenge.maikproba.rockpaperscissors.models.GameState;
import de.codechallenge.maikproba.rockpaperscissors.models.HandSign;
import de.codechallenge.maikproba.rockpaperscissors.models.Player;
import de.codechallenge.maikproba.rockpaperscissors.models.Round;
import org.springframework.ui.Model;

import java.util.*;

public class GameStateManager {
    private Map<String, GameState> games;

    public GameStateManager() {
        this.games = new HashMap<>();
    }

    public void startGame(Model model) {
        String gameUUID = createNewGame();

        model.addAttribute("gameUUID", gameUUID);
        model.addAttribute("roundIndex", "1");
        model.addAttribute("playerName", getGameStateByUUID(gameUUID).getPlayer1().getName());
        model.addAttribute("link", "/uuid/%s/round/%s/send".formatted(gameUUID, "1"));
    }

    public boolean isNextRound(String gameUUID, int roundIndex, HandSign handSign, Model model) {
        GameState gameState = getGameStateByUUID(gameUUID);
        Round round = gameState.getRounds().get(roundIndex - 1);

        String playerName = null;
        if (round.getHandSignPlayer1() == null) {
            round.setHandSignPlayer1(handSign);
            playerName = gameState.getPlayer2().getName();
        } else if (round.getHandSignPlayer2() == null) {
            round.setHandSignPlayer2(handSign);
            playerName = gameState.getPlayer1().getName();

            chooseWinnerOfRound(gameUUID, roundIndex);

            if (roundIndex < 3) {
                createNewRoundInGameByUUID(gameUUID);
                roundIndex++;
            } else if (roundIndex == 3) {
                Player player = chooseGameWinner(gameUUID);

                if (player == null) {
                    model.addAttribute("winner", "It's a tie!");
                } else {
                    model.addAttribute("winner", player.getName());
                }

                return false;
            }
        }

        model.addAttribute("gameUUID", gameUUID);
        model.addAttribute("roundIndex", roundIndex);
        model.addAttribute("playerName", playerName);
        model.addAttribute("link", "/uuid/%s/round/%s/send".formatted(gameUUID, roundIndex));

        return true;
    }

    public GameState getGameState(String gameUUID) {
        return getGameStateByUUID(gameUUID);
    }

    public String createNewGame() {
        String gameUUID = generateUUID();
        this.games.put(gameUUID, new GameState(new Player("Player1"), new Player("Player2")));

        createNewRoundInGameByUUID(gameUUID);
        return gameUUID;
    }

    public GameState getGameStateByUUID(String uuid) {
        return this.games.get(uuid);
    }

    private String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Round createNewRoundInGameByUUID(String uuid) {
        List<Round> rounds = this.games.get(uuid).getRounds();
        Round round = new Round();
        rounds.add(new Round());
        return round;
    }

    /**
     * 1. **Rock** crushes scissors (Rock wins)
     * 2. **Scissors** cuts paper (Scissors wins)
     * 3. **Paper** covers rock (Paper wins)
     */
    public Player chooseWinnerOfRound(String gameUUID, int roundIndex) {
        GameState gameState = this.games.get(gameUUID);
        Round round = gameState.getRounds().get(roundIndex - 1);

        // ROCK VS SCISSORS
        if (round.getHandSignPlayer1() == HandSign.ROCK && round.getHandSignPlayer2() == HandSign.SCISSORS){
            round.setWinner(gameState.getPlayer1());
            round.setResult("Player 1 wins!");
            return gameState.getPlayer1();
        }

        if (round.getHandSignPlayer2() == HandSign.ROCK && round.getHandSignPlayer1() == HandSign.SCISSORS){
            round.setWinner(gameState.getPlayer2());
            round.setResult("Player 2 wins!");
            return gameState.getPlayer2();
        }

        // SCISSORS VS PAPER
        if(round.getHandSignPlayer1() == HandSign.SCISSORS && round.getHandSignPlayer2() == HandSign.PAPER){
            round.setWinner(gameState.getPlayer1());
            round.setResult("Player 1 wins!");
            return gameState.getPlayer1();
        }

        if(round.getHandSignPlayer2() == HandSign.SCISSORS && round.getHandSignPlayer1() == HandSign.PAPER){
            round.setWinner(gameState.getPlayer2());
            round.setResult("Player 2 wins!");
            return gameState.getPlayer2();
        }

        // PAPER VS ROCK
        if(round.getHandSignPlayer1() == HandSign.PAPER && round.getHandSignPlayer2() == HandSign.ROCK){
            round.setWinner(gameState.getPlayer1());
            round.setResult("Player 1 wins!");
            return gameState.getPlayer1();
        }

        if(round.getHandSignPlayer2() == HandSign.PAPER && round.getHandSignPlayer1() == HandSign.ROCK){
            round.setWinner(gameState.getPlayer2());
            round.setResult("Player 2 wins!");
            return gameState.getPlayer2();
        }

        return null;
    }

    public Player chooseGameWinner(String gameUUID){
        int player1Counter = 0;
        int player2Counter = 0;

        GameState gameState = this.games.get(gameUUID);
        for(int i = 0; i<gameState.getRounds().size(); i++){
            Round round = gameState.getRounds().get(i);
            if(round.getWinner() == gameState.getPlayer1()){
                player1Counter++;
            }
            if(round.getWinner() == gameState.getPlayer2()){
                player2Counter++;
            }
        }

        if(player1Counter > player2Counter){
            return gameState.getPlayer1();
        }
        if(player1Counter < player2Counter){
            return gameState.getPlayer2();
        }
        return null;
    }
}
