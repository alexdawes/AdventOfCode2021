package day09;

import day08.Line;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day09 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var map = getInputs();
        var lowPoints = getLowPoints(map);
        return lowPoints.stream().reduce(0, (total, coord) -> total + map[coord.getJ()][coord.getI()] + 1, Integer::sum);
    }

    public static int part2() throws Exception {
        var map = getInputs();
        var height = map.length;
        var width = map[0].length;
        var lowPoints = getLowPoints(map);

        var basinSizes = new HashMap<Coordinate, Integer>();

        for (var lowPoint : lowPoints)
        {
            var toProcess = new ArrayList<Coordinate>();
            var processed = new HashSet<Coordinate>();
            toProcess.add(lowPoint);
            var size = 0;

            while (toProcess.size() > 0) {
                var next = toProcess.get(0);
                toProcess.remove(next);

                var value = map[next.getJ()][next.getI()];
                if (value != 9) {
                    size++;
                    var neighbours = getNeighbours(map, next);
                    var unprocessedNeighbours = neighbours.stream().filter(n -> !processed.contains(n)).toList();

                    for (var n : unprocessedNeighbours) {
                        if (!toProcess.contains(n)) {
                            toProcess.add(n);
                        }
                     }
                }
                processed.add(next);
            }

            basinSizes.put(lowPoint, size);
        }

        var values = basinSizes.values().toArray(new Integer[basinSizes.size()]);
        Arrays.sort(values);
        Arrays.sort(values, Collections.reverseOrder());

        return values[0] * values[1] * values[2];
    }

    public static List<Coordinate> getNeighbours(int[][] map, Coordinate coordinate)
    {
        var height = map.length;
        var width = map[0].length;
        var result = new ArrayList<Coordinate>();

        var i = coordinate.getI();
        var j = coordinate.getJ();

        if (i != 0) { result.add(new Coordinate(i-1, j)); }
        if (i != width - 1) { result.add(new Coordinate(i+1, j)); }
        if (j != 0) { result.add(new Coordinate(i, j-1)); }
        if (j != height - 1) { result.add(new Coordinate(i, j+1)); }

        return result;
    }

    public static List<Coordinate> getLowPoints(int[][] map) {
        var height = map.length;
        var width = map[0].length;

        var result = new ArrayList<Coordinate>();

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                var value = map[j][i];

                var neighbours = getNeighbours(map, new Coordinate(i, j));
                var neighbourValues = neighbours.stream().map(n -> map[n.getJ()][n.getI()]).toList();

                if (neighbourValues.stream().allMatch(nv -> nv > value)) {
                    result.add(new Coordinate(i, j));
                }
            }
        }
        return result;
    }

    public static int[][] getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day09/input"));
        var height = lines.size();
        var width = lines.get(0).length();

        var result = new int[height][width];

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                result[j][i] = Character.getNumericValue(lines.get(j).charAt(i));
            }
        }

        return result;
    }
}
