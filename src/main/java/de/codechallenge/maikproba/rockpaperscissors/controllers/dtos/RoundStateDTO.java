package de.codechallenge.maikproba.rockpaperscissors.controllers.dtos;

public class RoundStateDTO {
    public String gameUUID;
    public int roundIndex;
    public String playerName;
    public String linkForNextRound;

    public String winnerOfGame;

    public RoundStateDTO(String gameUUID, int roundIndex, String playerName, String linkForNextRound) {
        this.gameUUID = gameUUID;
        this.roundIndex = roundIndex;
        this.playerName = playerName;
        this.linkForNextRound = linkForNextRound;
    }

    public RoundStateDTO(String gameUUID, int roundIndex, String playerName) {
        this.gameUUID = gameUUID;
        this.roundIndex = roundIndex;
        this.playerName = playerName;
    }
}
