package de.codechallenge.maikproba.rockpaperscissors.services;


import de.codechallenge.maikproba.rockpaperscissors.controllers.dtos.RoundStateDTO;
import de.codechallenge.maikproba.rockpaperscissors.models.GameState;
import de.codechallenge.maikproba.rockpaperscissors.models.HandSign;
import de.codechallenge.maikproba.rockpaperscissors.models.Player;
import de.codechallenge.maikproba.rockpaperscissors.models.Round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameStateManager {
    private final Map<String, GameState> games;

    public GameStateManager() {
        this.games = new HashMap<>();
    }

    /**
     * starts the game
     * @return RoundStateDTO with gameUUID, roundIndex, playerName, a link for stateless game step navigation.
     */
    public RoundStateDTO startGame() {
        String gameUUID = createNewGame();
        String player1Name = getGameStateByUUID(gameUUID).getPlayer1().getName();
        String linkForNextRound = "/uuid/%s/round/%s/send".formatted(gameUUID, "1");
        return new RoundStateDTO(gameUUID, 1, player1Name, linkForNextRound);
    }

    /**
     * creates a new game
     * @return gameUUID to identify the game for results in json for example
     */
    public String createNewGame() {
        String gameUUID = generateUUID();
        this.games.put(gameUUID, new GameState(new Player("Player1"), new Player("Player2")));

        createNewRoundInGameByUUID(gameUUID);
        return gameUUID;
    }

    /**
     * determines if another round should be played or not.
     * @param gameUUID current game uuid
     * @param roundIndex current game round
     * @param handSign current choosen handsign by player
     * @return state of this round
     */
    public RoundStateDTO simulateRound(String gameUUID, int roundIndex, HandSign handSign) {
        GameState gameState = getGameStateByUUID(gameUUID);
        Round round = gameState.getRounds().get(roundIndex - 1);

        String nextPlayerName = null;
        if (round.getHandSignPlayer1() == null) {
            round.setHandSignPlayer1(handSign);
            nextPlayerName = gameState.getPlayer2().getName();
        } else if (round.getHandSignPlayer2() == null) {
            round.setHandSignPlayer2(handSign);
            nextPlayerName = gameState.getPlayer1().getName();

            chooseWinnerOfRound(gameUUID, roundIndex);

            if (roundIndex < 3) {
                createNewRoundInGameByUUID(gameUUID);
                roundIndex++;
            } else if (roundIndex == 3) {
                Player player = chooseGameWinner(gameUUID);
                RoundStateDTO roundStateDTO = new RoundStateDTO(gameUUID, roundIndex, nextPlayerName);
                if (player == null) {
                    roundStateDTO.winnerOfGame = "It's a tie!";
                } else {
                    roundStateDTO.winnerOfGame = player.getName();
                }
                return roundStateDTO;
            }
        }
        String nextRoundLink = "/uuid/%s/round/%s/send".formatted(gameUUID, roundIndex);
        return new RoundStateDTO(gameUUID, roundIndex, nextPlayerName, nextRoundLink);
    }

    public GameState getGameStateByUUID(String uuid) {
        return this.games.get(uuid);
    }

    public void createNewRoundInGameByUUID(String uuid) {
        List<Round> rounds = this.games.get(uuid).getRounds();
        rounds.add(new Round());
    }

    /**
     * Rules:
     * 1. Rock crushes scissors (Rock wins)
     * 2. Scissors cuts paper (Scissors wins)
     * 3. Paper covers rock (Paper wins)
     * @param gameUUID identifies the game
     * @param roundIndex currnet roundIndex
     * @return the winner of this round
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

        round.setResult("It's a tie!");
        return null;
    }

    /**
     * chooses a player after game has ended.
     * @param gameUUID identifies the game
     * @return the winner of the whole game
     */
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

    /**
     *
     * @return generates a UUID
     */
    private String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
