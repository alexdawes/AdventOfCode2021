package day21.models;

import day21.models.dice.DeterministicDie;
import day21.models.dice.DiracDie;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Player player1;
    private Player player2;
    private int playerPointer;
    private int targetScore;

    public Game(Player player1, Player player2, int targetScore) {
        this(player1, player2, 0, targetScore);
    }

    public Game(Player player1, Player player2, int playerPointer, int targetScore) {
        this.player1 = player1;
        this.player2 = player2;
        this.playerPointer = playerPointer;
        this.targetScore = targetScore;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Game)) {
            return false;
        }

        var g = (Game)o;

        return player1.equals(g.player1) &&
                player2.equals(g.player2) &&
                playerPointer == g.playerPointer &&
                targetScore == g.targetScore;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + player1.hashCode();
        hash = hash * 31 + player2.hashCode();
        hash = hash * 31 + playerPointer;
        hash = hash * 31 + targetScore;
        return hash;
    }

    public Game clone() {
        return new Game(player1.clone(), player2.clone(), playerPointer, targetScore);
    }

    private Player getCurrentPlayer() {
        return playerPointer == 0 ? player1 : player2;
    }

    private Player getOtherPlayer() {
        return playerPointer == 0 ? player2 : player1;
    }

    private void swapPlayer() {
        playerPointer = 1 - playerPointer;
    }

    private void handleRolls(int roll1, int roll2, int roll3) {
        var sum = roll1 + roll2 + roll3;

        var currentPlayer = getCurrentPlayer();
        var position = currentPlayer.getPosition();

        var newPosition = position + sum;
        while (newPosition > 10) {
            newPosition -= 10;
        }

        currentPlayer.setPosition(newPosition);
        currentPlayer.increaseScore(newPosition);

        swapPlayer();
    }

    public boolean player1Wins() {
        return player1.getScore() >= targetScore;
    }

    public boolean player2Wins() {
        return player2.getScore() >= targetScore;
    }

    public int playDeterministic() {
        var die = new DeterministicDie();
        while (true) {
            handleRolls(die.roll(), die.roll(), die.roll());

            if (player1Wins()) {
                return player2.getScore() * die.getRollCount();
            }
            if (player2Wins()) {
                return player1.getScore() * die.getRollCount();
            }
        }
    }

    public long playDirac() {
        var results = playDirac(new HashMap<>());
        return Math.max(results[0], results[1]);
    }

    private long[] playDirac(Map<Game, long[]> resultsCache) {
        if (resultsCache.containsKey(this)) {
            return resultsCache.get(this);
        }
        var die = DiracDie.Instance;

        long[] results = {0, 0};
        for (var roll1 : die.roll()) {
            for (var roll2 : die.roll()) {
                for (var roll3 : die.roll()) {
                    var game = clone();
                    game.handleRolls(roll1, roll2, roll3);

                    long[] result;
                    if (game.player1Wins()) {
                        long[] r = {1, 0};
                        resultsCache.put(game, r);
                        result = r;
                    }
                    else if (game.player2Wins()) {
                        long[] r = {0, 1};
                        resultsCache.put(game, r);
                        result = r;
                    }
                    else {
                        result = game.playDirac(resultsCache);
                    }

                    results[0] += result[0];
                    results[1] += result[1];
                }
            }
        }

        resultsCache.put(this, results);

        return results;
    }
}
