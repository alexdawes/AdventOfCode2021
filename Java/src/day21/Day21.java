package day21;

import day20.models.Data;
import day20.models.Processor;
import day21.models.Game;
import day21.models.Player;
import day21.models.dice.DeterministicDie;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day21 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var players = getInput();
        var game = new Game(players.get(0), players.get(1), 1000);
        return game.playDeterministic();
    }

    public static long part2() throws Exception {
        var players = getInput();
        var game = new Game(players.get(0), players.get(1), 21);
        return game.playDirac();
    }
    public static List<Player> getInput() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day21/input"));
        return lines.stream().map(l -> Player.parse(l)).toList();
    }
}
