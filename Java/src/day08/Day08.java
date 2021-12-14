package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day08 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var simpleLengths = Arrays.asList(2, 3, 4, 7);
        var lines = getInputs();
        return lines.stream().reduce(0L, (total, line) -> {
            var outputs = line.getOutputPatterns();
            var simpleOutputs = Arrays.stream(outputs).filter(o -> simpleLengths.contains(o.length())).count();
            return simpleOutputs + total;
        }, Long::sum);
    }

    public static int part2() throws Exception {
        var lines = getInputs();
        return lines.stream().reduce(0, (total, line) -> total + line.solveOutput(), Integer::sum);
    }

    public static List<Line> getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day08/input"));
        return lines.stream().map(l -> Line.Parse(l)).toList();
    }
}
