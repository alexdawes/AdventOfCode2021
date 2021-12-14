package day04.bingo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BingoGame {
    private List<Integer> numbers;
    private List<BingoBoard> boards;

    public BingoGame(List<Integer> numbers, List<BingoBoard> boards) {
        this.numbers = numbers;
        this.boards = boards;
    }

    public List<Integer> getNumbers() { return numbers; }
    public List<BingoBoard> getBoards() { return boards; }

    public static BingoGame parse(String s) {
        var strings = s.split("\r\n\r\n");

        var first = strings[0];

        var numbers = Arrays.stream(first.split(",")).map(c -> Integer.parseInt(c)).toList();

        var boards = new ArrayList<BingoBoard>();

        for (var b = 1; b < strings.length; b++) {
            var boardString = strings[b];
            var rows = boardString.split("\r\n");
            var rowCount = rows.length;
            var colCount = rows[0].trim().split("\s+").length;

            var board = new int[rowCount][colCount];
            for (var j = 0; j < rowCount; j++) {
                var row = rows[j].trim().split("\s+");
                for (var i = 0; i < colCount; i++) {
                    var value = Integer.parseInt(row[i]);
                    board[j][i] = value;
                }
            }
            boards.add(new BingoBoard(board));
        }

        return new BingoGame(numbers, boards);
    }
}
