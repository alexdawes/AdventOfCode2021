package day15;

import day14.PolymerSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class Day15 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var map = getInputs();
        var height = map.length;
        var width = map[0].length;

        var path = astar(map, new Coordinate(0, 0), new Coordinate(width - 1, height - 1));

        var total = 0;
        for (var i = 1; i < path.size(); i++) {
            var c = path.get(i);
            total += map[c.getY()][c.getX()];
        }
        return total;
    }

    public static long part2() throws Exception {
        var map = getInputs();

        var height = map.length;
        var width = map[0].length;

        var largeMap = new int[height * 5][width * 5];

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                var value = map[j][i];
                for (var fx = 0; fx < 5; fx++) {
                    for (var fy = 0; fy < 5; fy++) {
                        var fv = fx + fy;
                        var newValue = value + fv;
                        if (newValue > 9) {
                            newValue -= 9;
                        }
                        largeMap[j + height * fy][i + width * fx] = newValue;
                    }
                }
            }
        }

        var path = astar(largeMap, new Coordinate(0, 0), new Coordinate((5 * width) - 1, (5 * height) - 1));

        var total = 0;
        for (var i = 1; i < path.size(); i++) {
            var c = path.get(i);
            total += largeMap[c.getY()][c.getX()];
        }
        return total;
    }

    public static List<Coordinate> astar(int[][] map, Coordinate start, Coordinate end) {
        var height = map.length;
        var width = map[0].length;

        var previous = new HashMap<Coordinate, Coordinate>();

        var gScore = new HashMap<Coordinate, Integer>();
        var fScore = new HashMap<Coordinate, Integer>();

        Function<Coordinate, Integer> h = c -> Math.abs(c.getX() - end.getX()) + Math.abs(c.getY() - end.getY());

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                var c = new Coordinate(i, j);
                gScore.put(c, c.equals(start) ? 0 : Integer.MAX_VALUE);
                fScore.put(c, c.equals(start) ? h.apply(start) : Integer.MAX_VALUE);
            }
        }

        var openSet = new PriorityQueue<Coordinate>((c1, c2) -> fScore.get(c1) - fScore.get(c2));
        openSet.add(start);

        while (openSet.size() > 0) {
            var current = openSet.poll();
            if (current.equals(end)) {
                return constructPath(start, end, previous);
            }

            openSet.remove(current);

            var neighbours = getNeighbours(map, current);
            for (var neighbour : neighbours) {
                var tentativeGScore = gScore.get(current) + map[neighbour.getY()][neighbour.getX()];
                if (tentativeGScore < gScore.get(neighbour)) {
                    previous.put(neighbour, current);
                    gScore.put(neighbour, tentativeGScore);
                    fScore.put(neighbour, tentativeGScore + h.apply(neighbour));
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }

        return null;
    }

    public static List<Coordinate> dijkstra(int[][] map, Coordinate start, Coordinate end) {
        var height = map.length;
        var width = map[0].length;

        var unvisited = new HashSet<Coordinate>();
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                unvisited.add(new Coordinate(i, j));
            }
        }

        var tentativeDistance = new HashMap<Coordinate, Integer>();
        var previous = new HashMap<Coordinate, Coordinate>();
        for(var coord : unvisited) {
            tentativeDistance.put(coord, coord.equals(start) ? 0 : Integer.MAX_VALUE);
        }

        var current = start;

        while (true) {
            var currentDistance = tentativeDistance.get(current);
            var neighbours = getNeighbours(map, current);
            var unvisitedNeighbours = neighbours.stream().filter(unvisited::contains).toList();

            for (var c : unvisitedNeighbours) {
                var newDistance = currentDistance + map[c.getY()][c.getX()];
                if (newDistance < tentativeDistance.get(c)) {
                    tentativeDistance.put(c, newDistance);
                    previous.put(c, current);
                }
            }

            unvisited.remove(current);

            if (current.equals(end)) {
                break;
            }

            Coordinate next = null;
            for (var c : unvisited) {
                if (next == null || tentativeDistance.get(c) < tentativeDistance.get(next)) {
                    next = c;
                }
            }

            current = next;
        }

        return constructPath(start, end, previous);
    }

    public static List<Coordinate> constructPath(Coordinate start, Coordinate end, Map<Coordinate, Coordinate> previous) {
        var reversePath = new ArrayList<Coordinate>();
        reversePath.add(end);

        var current = end;
        while (current != start) {
            current = previous.get(current);
            reversePath.add(current);
        }

        var path = new ArrayList<Coordinate>();
        for (var i = reversePath.size() - 1; i >= 0; i--) {
            path.add(reversePath.get(i));
        }

        return path;
    }

    public static List<Coordinate> getNeighbours(int[][] arr, Coordinate coord) {
        var height  = arr.length;
        var width = arr[0].length;

        var x = coord.getX();
        var y = coord.getY();

        var neighbours = new ArrayList<Coordinate>();
        neighbours.add(new Coordinate(x-1, y));
        neighbours.add(new Coordinate(x, y-1));
        neighbours.add(new Coordinate(x, y+1));
        neighbours.add(new Coordinate(x+1, y));

        return neighbours.stream().filter(n -> {
            var nx = n.getX();
            var ny = n.getY();
            return 0 <= nx && nx < width && 0 <= ny && ny < height;
        }).toList();
    }

    public static int[][] getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day15/input"));

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
