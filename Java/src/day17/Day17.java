package day17;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day17 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var system = getTrench();
        var yMin = system.getTarget().getYRange().getMin();
        var y = Math.abs(yMin);
        return y * (y - 1) / 2;
    }

    public static long part2() throws Exception {
        var trench = getTrench();

        var yMin = trench.getTarget().getYRange().getMin();
        var yMax = Math.abs(yMin);
        var xMin = 0;
        var xMax = trench.getTarget().getXRange().getMax();

        var possibilities = new ArrayList<Coordinate>();
        for (var x = xMin; x <= xMax; x++) {
            for (var y = yMin; y <= yMax; y++) {
                possibilities.add(new Coordinate(x, y));
            }
        }
        return possibilities
                .parallelStream()
                .filter(p -> trench.Test(p.getX(), p.getY()))
                .reduce(0, (a, b) -> a + 1, Integer::sum);
    }

    public static Trench getTrench() throws Exception {
        var line = Files.readString(Path.of("src/day17/input")).trim();
        var system = Trench.parse(line);
        return system;
    }
}
