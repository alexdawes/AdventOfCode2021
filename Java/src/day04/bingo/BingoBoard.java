package day04.bingo;

import java.util.Arrays;

public class BingoBoard {
    private BingoCell[][] board;

    public BingoBoard(int[][] board) {
        var y = board.length;
        var x = y == 0 ? 0 : board[0].length;

        this.board = new BingoCell[y][x];

        for (var j = 0; j < y; j++) {
            for (var i = 0; i < x; i++) {
                this.board[j][i] = new BingoCell(board[j][i], false);
            }
        }
    }

    public void accept(int value) {
        for (var row : board) {
            for (var cell : row) {
                cell.accept(value);
            }
        }
    }

    public boolean hasWon() {
        for (var row : board) {
            if (Arrays.stream(row).allMatch(c -> c.getChecked())) {
                return true;
            }
        }

        for (var i = 0; i < board[0].length; i++) {
            var column = new BingoCell[board.length];
            for (var j = 0; j < board.length; j++) {
                column[j] = board[j][i];
            }

            if (Arrays.stream(column).allMatch(c -> c.getChecked())) {
                return true;
            }
        }

        return false;
    }

    public int getUnmarkedSum() {
        var sum = 0;
        for (var row : board) {
            for (var cell : row) {
                if (!cell.getChecked()) {
                    sum += cell.getValue();
                }
            }
        }

        return sum;
    }
}
