package day21.models;

import java.util.regex.Pattern;

public class Player {
    private int id;
    private int position;
    private int score;

    public Player(int id, int position) {
        this(id, position, 0);
    }

    public Player(int id, int position, int score) {
        this.id = id;
        this.position = position;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int value) {
        position = value;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int value) {
        score += value;
    }

    public static Player parse(String line) {
        var pattern = Pattern.compile("^Player (?<id>\\d+) starting position: (?<pos>\\d+)$");

        var matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            return null;
        }

        var id = Integer.parseInt(matcher.group("id"));
        var position = Integer.parseInt(matcher.group("pos"));

        return new Player(id, position);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Player &&
                ((Player)o).id == id &&
                ((Player)o).position == position &&
                ((Player)o).score == score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id;
        hash = 31 * hash + position;
        hash = 31 * hash + score;
        return hash;
    }

    public Player clone() {
        return new Player(id, position, score);
    }
}
