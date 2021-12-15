package day12;

import day11.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12 {
    public static String END = "end";
    public static String START = "start";

    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var system = getCaves();

        var path = Arrays.asList(START);

        var paths = walk(path, system);

        return paths.size();
    }

    public static long part2() throws Exception {
        var system = getCaves();

        var path = Arrays.asList(START);

        var paths = walkAdvanced(path, system);

        return paths.size();
    }

    public static List<List<String>> walk(List<String> path, HashMap<String, List<String>> system) {
        var current = path.get(path.size() - 1);

        if (current.equals(END)) {
            return Arrays.asList(path);
        }

        var neighbours = system.get(current);
        var available = neighbours.stream().filter(n -> {
            return isLarge(n) || !path.contains(n);
        }).toList();

        var result = new ArrayList<List<String>>();
        for (var next : available) {
            var newPath = new ArrayList<String>(path);
            newPath.add(next);
            var paths = walk(newPath, system);
            result.addAll(paths);
        }
        return result;
    }

    public static List<List<String>> walkAdvanced(List<String> path, HashMap<String, List<String>> system) {
        var current = path.get(path.size() - 1);

        if (current.equals(END)) {
            return Arrays.asList(path);
        }

        var neighbours = system.get(current);
        var available = neighbours.stream().filter(n -> {
            if (isLarge(n)) {
                return true;
            }
            var pathVisitedSmallCaveTwice = false;
            for (var i = 0; i < path.size(); i++) {
                var pi = path.get(i);
                if (isSmall(pi)) {
                    for (var j = i + 1; j < path.size(); j++) {
                        var pj = path.get(j);
                        if (pi.equals(pj)) {
                            pathVisitedSmallCaveTwice = true;
                        }
                    }
                }
            }
            if (n.equals(START) || n.equals(END) || pathVisitedSmallCaveTwice) {
                return !path.contains(n);
            }
            return true;
        }).toList();

        var result = new ArrayList<List<String>>();
        for (var next : available) {
            var newPath = new ArrayList<String>(path);
            newPath.add(next);
            var paths = walkAdvanced(newPath, system);
            result.addAll(paths);
        }
        return result;
    }

    public static boolean isSmall(String cave) {
        return cave.toLowerCase(Locale.ROOT) == cave;
    }
    public static boolean isLarge(String cave) {
        return cave.toUpperCase(Locale.ROOT) == cave;
    }

    public static HashMap<String, List<String>> getCaves() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day12/input"));

        var map = new HashMap<String, List<String>>();

        for (var line : lines) {
            var split = line.split("-");
            var first = split[0];
            var second = split[1];

            if (!map.containsKey(first)) {
                map.put(first, new ArrayList<String>());
            }
            if (!map.containsKey(second)) {
                map.put(second, new ArrayList<String>());
            }

            map.get(first).add(second);
            map.get(second).add(first);
        }

        var caves = map.keySet();
        var result = new HashMap<String, List<String>>();
        for (var cave : caves) {
            result.put(cave, map.get(cave));
        }
        return result;
    }
}
