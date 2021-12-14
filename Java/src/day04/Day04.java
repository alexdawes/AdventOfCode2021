package day04;


import day04.bingo.BingoBoard;
import day04.bingo.BingoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day04 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static Integer part1() throws Exception {
        var game = getGame();
        var numbers = game.getNumbers();
        var boards = game.getBoards();

        for (var number : numbers) {
            for (var board : boards) {
                board.accept(number);
                if (board.hasWon()) {
                    return board.getUnmarkedSum() * number;
                }
            }
        }

        return -1;
    }

    public static Integer part2() throws Exception {
        var game = getGame();
        var numbers = game.getNumbers();
        var boards = game.getBoards();

        for (var number : numbers) {
            for (var board : new ArrayList<BingoBoard>(boards)) {
                board.accept(number);
                if (board.hasWon()) {
                    if (boards.size() == 1) {
                        return board.getUnmarkedSum() * number;
                    }

                    boards.remove(board);
                }
            }
        }

        return -1;
    }

    public static BingoGame getGame() throws IOException {
        var s = Files.readString(Path.of("src/day04/input"));
        return BingoGame.parse(s);
    }

}
