package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day07 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static int part1() throws Exception {
        var crabs = getInputs();

        var min = crabs.stream().min(Integer::compare).get();
        var max = crabs.stream().max(Integer::compare).get();

        var result = Integer.MAX_VALUE;
        for (var i = min; i <= max; i++) {
            var cost = countFuelSimple(crabs, i);
            if (cost < result) {
                result = cost;
            }
        }
        return result;
    }

    public static int part2() throws Exception {
        var crabs = getInputs();

        var min = crabs.stream().min(Integer::compare).get();
        var max = crabs.stream().max(Integer::compare).get();

        var result = Integer.MAX_VALUE;
        for (var i = min; i <= max; i++) {
            var cost = countFuelAdvanced(crabs, i);
            if (cost < result) {
                result = cost;
            }
        }
        return result;
    }

    public static int countFuelSimple(List<Integer> crabs, int position) {
        return crabs.stream().map(c -> Math.abs(c - position)).reduce(0, (a, b) -> a + b);
    }

    public static int countFuelAdvanced(List<Integer> crabs, int position) {
        return crabs.stream().map(c -> Math.abs(c - position)).map(c -> (c * (c + 1)) / 2).reduce(0, (a, b) -> a + b);
    }

    public static List<Integer> getInputs() throws IOException {
        var line = Files.readString(Path.of("src/day07/input"));
        return Arrays.stream(line.split(",")).map((f -> Integer.parseInt(f))).toList();
    }
}
