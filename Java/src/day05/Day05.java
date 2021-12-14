package day05;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Day05 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static Integer part1() throws Exception {
        var lineSegments = getInputs().stream().filter(ls -> ls.isHorizontalOrVertical()).toList();

        return countOverlaps(lineSegments);
    }

    public static Integer part2() throws Exception {
        var lineSegments = getInputs();
        return countOverlaps(lineSegments);
    }

    public static Integer countOverlaps(List<LineSegment> lineSegments) {
        var coordinateCounts = new HashMap<Coordinate, Integer>();
        for (var lineSeg : lineSegments) {
            var coords = lineSeg.GetCoordinates();
            for (var coord : coords) {
                if (!coordinateCounts.containsKey(coord)) {
                    coordinateCounts.put(coord, 0);
                }
                var curr = coordinateCounts.get(coord);
                coordinateCounts.put(coord, curr + 1);
            }
        }

        return coordinateCounts.values().stream().reduce(0, (total, el) -> el >= 2 ? total + 1 : total);
    }

    public static List<LineSegment> getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day05/input"));
        return lines.stream().map((l -> LineSegment.Parse(l))).toList();
    }
}
