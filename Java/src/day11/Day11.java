package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var arr = getInputs();

        var count = 0;
        for (var i = 0; i < 100; i++) {
            count += Step(arr);
        }
        return count;
    }

    public static long part2() throws Exception {
        var arr = getInputs();
        var height = arr.length;
        var width = arr[0].length;

        var count = 0;
        while (true) {
            count++;
            var flashes = Step(arr);
            if (flashes == width * height) {
                return count;
            }
        }
    }

    public static int Step(int[][] arr)
    {
        var height = arr.length;
        var width = arr[0].length;

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                arr[j][i]++;
            }
        }

        var flashCount = IterateFlashes(arr);

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                if (arr[j][i] > 9) {
                    arr[j][i] = 0;
                }
            }
        }

        return flashCount;
    }

    public static int IterateFlashes(int[][] arr) {
        var height = arr.length;
        var width = arr[0].length;
        var flashes = new boolean[height][width];
        return IterateFlashes(arr, flashes);
    }

    public static int IterateFlashes(int[][] arr, boolean[][] flashes) {
        var height = arr.length;
        var width = arr[0].length;
        var count  = 0;
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                if (arr[j][i] > 9 && !flashes[j][i]) {
                    flashes[j][i] = true;
                    var neighbours = getNeighbours(arr, new Coordinate(i, j));
                    for (var n : neighbours) {
                        arr[n.getJ()][n.getI()]++;
                    }
                    count++;
                }
            }
        }

        if (count > 0) {
            count += IterateFlashes(arr, flashes);
        }

        return count;
    }

    public static List<Coordinate> getNeighbours(int[][] arr, Coordinate coord) {
        var height  = arr.length;
        var width = arr[0].length;

        var i = coord.getI();
        var j = coord.getJ();

        var neighbours = new ArrayList<Coordinate>();
        neighbours.add(new Coordinate(i-1, j-1));
        neighbours.add(new Coordinate(i-1, j));
        neighbours.add(new Coordinate(i-1, j+1));
        neighbours.add(new Coordinate(i, j-1));
        neighbours.add(new Coordinate(i, j+1));
        neighbours.add(new Coordinate(i+1, j-1));
        neighbours.add(new Coordinate(i+1, j));
        neighbours.add(new Coordinate(i+1, j+1));

        return neighbours.stream().filter(n -> {
            var ni = n.getI();
            var nj = n.getJ();
            return 0 <= ni && ni < width && 0 <= nj && nj < height;
        }).toList();
    }

    public static int[][] getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day11/input"));

        var height = lines.size();
        var width = lines.get(0).length();

        var arr = new int[height][width];
        for (var j = 0; j < height; j++) {
            for (var i = 0; i < width; i++) {
                var c = lines.get(j).charAt(i);
                var o = Character.getNumericValue(c);
                arr[j][i] = o;
            }
        }
        return arr;
    }
}
