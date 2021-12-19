package day19;

import day19.models.*;
import day19.models.Scanner;
import day19.models.Vector;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day19 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var scanners = getScanners();

        process(scanners);

        var allBeacons = new HashSet<Vector>();
        for (var scanner : scanners) {
            allBeacons.addAll(scanner.getBeacons());
        }

        return allBeacons.size();
    }

    public static long part2() throws Exception {
        var scanners = getScanners();

        process(scanners);

        var max = 0;
        for (var scanner : scanners) {
            for (var scanner2 : scanners) {
                var distance = scanner.getPosition().distanceTo(scanner2.getPosition());
                if (distance > max) {
                    max = distance;
                }
            }
        }
        return max;
    }

    public static void process(List<Scanner> scanners) throws Exception {
        var candidates = new ArrayList<Pair<Scanner>>();
        for (var i = 0; i < scanners.size() - 1; i++) {
            for (var j = i + 1; j < scanners.size(); j++) {
                var iScanner = scanners.get(i);
                var jScanner = scanners.get(j);

                if (iScanner.testAgainst(jScanner)) {
                    candidates.add(new Pair<>(iScanner, jScanner));
                }
            }
        }

        var processed = new ArrayList<Scanner>();
        processed.add(scanners.get(0));

        var unprocessed = new ArrayList<Scanner>();
        unprocessed.addAll(scanners.stream().skip(1).toList());

        while (unprocessed.size() > 0) {
            var success = false;
            for (var pair : candidates) {
                Scanner next = null;
                Scanner base = null;
                if (processed.contains(pair.getLeft()) && unprocessed.contains(pair.getRight())) {
                    next = pair.getRight();
                    base = pair.getLeft();
                }
                if (processed.contains(pair.getRight()) && unprocessed.contains(pair.getLeft())) {
                    next = pair.getLeft();
                    base = pair.getRight();
                }
                if (next != null && base != null) {
                    var fitted = next.fitTo(base);
                    if (fitted) {
                        unprocessed.remove(next);
                        processed.add(next);
                        success = true;
                        break;
                    }
                }
            }

            if (!success) {
                throw new Exception("Nothing changed");
            }
        }
    }

    public static List<Scanner> getScanners() throws Exception {
        var line = Files.readString(Path.of("src/day19/input"));
        var scanners = Scanner.parseAll(line);
        return scanners;
    }
}
