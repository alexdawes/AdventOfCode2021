package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import static java.util.Arrays.stream;

public class Day06 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static Long part1() throws Exception {
        var counts = getInputs();

        for (var i = 0; i < 80; i++) {
           counts = iterate(counts);
        }

        return counts.values().stream().reduce(0L, (t, el) -> el + t);
    }

    public static Long part2() throws Exception {
        var counts = getInputs();

        for (var i = 0; i < 256; i++) {
            counts = iterate(counts);
        }

        return counts.values().stream().reduce(0L, (t, el) -> el + t);
    }

    public static HashMap<Integer, Long> iterate(HashMap<Integer, Long> counts) {
        var newCounts = new HashMap<Integer, Long>();

        for(var key : counts.keySet()) {
            var value = counts.get(key);
            if (key == 0) {
                if (!newCounts.containsKey(6)) {
                    newCounts.put(6, 0L);
                }
                newCounts.put(6, newCounts.get(6) + value);

                if (!newCounts.containsKey(8)) {
                    newCounts.put(8, 0L);
                }
                newCounts.put(8, newCounts.get(8) + value);
            }
            else {
                if (!newCounts.containsKey(key - 1)) {
                    newCounts.put(key - 1, 0L);
                }
                newCounts.put(key - 1, newCounts.get(key - 1) + value);
            }
        }

        return newCounts;
    }

    public static HashMap<Integer, Long> getInputs() throws IOException {
        var line = Files.readString(Path.of("src/day06/input"));
        var fish = Arrays.stream(line.split(",")).map((f -> Integer.parseInt(f))).toList();
        var result = new HashMap<Integer, Long>();

        for (var f : fish) {
            if (!result.containsKey(f)) {
                result.put(f, 0L);
            }
            var curr = result.get(f);
            result.put(f, curr + 1);
        }

        return result;
    }
}
