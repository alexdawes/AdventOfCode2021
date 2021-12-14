package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day01 {
    public static void main(String[] args) throws IOException {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static Integer part1() throws IOException {
        var depths = GetDepths();

        return CountIncreases(depths);
    }

    public static Integer part2() throws IOException {
        var depths = GetDepths();

        var rollingSums = new ArrayList<Integer>();
        for (var i = 0; i < depths.size() - 2; i++) {
            rollingSums.add(depths.get(i) + depths.get(i + 1) + depths.get(i + 2));
        }

        return CountIncreases(rollingSums);
    }

    public static Integer CountIncreases(List<Integer> depths) {
        var count = 0;

        var prev = depths.get(0);
        for (var i = 0; i < depths.size(); i += 1) {
            var next = depths.get(i);
            if (next > prev) {
                count++;
            }
            prev = next;
        }

        return count;
    }

    public static List<Integer> GetDepths() throws IOException {
        var inputs = Files.readAllLines(Path.of("src/day01/input"));
        var depths = inputs.stream().map(i -> Integer.parseInt(i)).toList();
        return depths;
    }
}
