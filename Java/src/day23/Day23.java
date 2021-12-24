package day23;

import day23.models.Burrow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;

public class Day23 {
    public static void main(String[] args) throws Exception {
//        var p1 = part1();
//        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var lines = getInput();
        var burrow = Burrow.parse(lines);

        return astar(burrow);
    }

    public static Long astar(Burrow burrow) {
        var openSet = new ArrayList<Burrow>(); // new PriorityQueue<Burrow>((b1, b2) -> b1.getEstimate() - b2.getEstimate());
        openSet.add(burrow);

        while (openSet.size() > 0) {
            openSet.sort(Comparator.comparingInt(Burrow::getHeuristic));
            var current = openSet.get(0);
            if (current.isComplete()) {
                return (long)current.getEnergy();
            }
            openSet.remove(current);
            for (var next : current.getNext()) {
                openSet.add(next);
            }
        }

        return -1L;
    }

    public static Long solve(Burrow burrow) {
        if (burrow.isComplete()) {
            return (long)burrow.getEnergy();
        }

        Long least = null;
        var next = burrow.getNext();
        for (var b : next) {
            var n = solve(b);
            if (least == null || (n != null && n < least)) {
                least = n;
            }
        }
        return least;
    }

    public static long part2() throws Exception {
        var lines = getInput();
        var newLines = new ArrayList<String>();
        for (var i = 0; i < 3; i++) {
            newLines.add(lines.get(i));
        }
        newLines.add("  #D#C#B#A#");
        newLines.add("  #D#B#A#C#");

        for (var i = 3; i < lines.size(); i++) {
            newLines.add(lines.get(i));
        }
        var burrow = Burrow.parse(newLines);

        return astar(burrow);
    }

    public static List<String> getInput() throws Exception {
        return Files.readAllLines(Path.of("src/day23/input"));
    }
}
