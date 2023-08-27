package de.codechallenge.maikproba.rockpaperscissors.models;

public class Round {
    private HandSign handSignPlayer1;
    private HandSign handSignPlayer2;
    private String result;
    private Player winner;

    public HandSign getHandSignPlayer1() {
        return handSignPlayer1;
    }

    public void setHandSignPlayer1(HandSign handSignPlayer1) {
        this.handSignPlayer1 = handSignPlayer1;
    }

    public HandSign getHandSignPlayer2() {
        return handSignPlayer2;
    }

    public void setHandSignPlayer2(HandSign handSignPlayer2) {
        this.handSignPlayer2 = handSignPlayer2;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
