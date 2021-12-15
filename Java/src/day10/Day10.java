package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10 {
    private static HashMap<Character, Character> BRACE_MAP = new HashMap<Character, Character>();
    private static HashMap<Character, Integer> ILLEGAL_POINTS_MAP = new HashMap<Character, Integer>();
    private static HashMap<Character, Integer> INCOMPLETE_POINTS_MAP = new HashMap<Character, Integer>();

    static {
        BRACE_MAP.put('(', ')');
        BRACE_MAP.put('[', ']');
        BRACE_MAP.put('{', '}');
        BRACE_MAP.put('<', '>');

        ILLEGAL_POINTS_MAP.put(')', 3);
        ILLEGAL_POINTS_MAP.put(']', 57);
        ILLEGAL_POINTS_MAP.put('}', 1197);
        ILLEGAL_POINTS_MAP.put('>', 25137);

        INCOMPLETE_POINTS_MAP.put(')', 1);
        INCOMPLETE_POINTS_MAP.put(']', 2);
        INCOMPLETE_POINTS_MAP.put('}', 3);
        INCOMPLETE_POINTS_MAP.put('>', 4);
    }

    public static boolean isOpenBrace(Character c) {
        return BRACE_MAP.containsKey(c);
    }
    public static boolean isCloseBrace(Character c) {
        return BRACE_MAP.containsValue(c);
    }

    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var lines = getInputs();

        var total = 0;
        for (var line : lines) {
            total += calculateErrorScore(line);
        }

        return total;
    }

    public static long part2() throws Exception {
        var lines = getInputs();

        var incompleteLines = lines.stream().filter(l -> calculateErrorScore(l) == 0).toList();

        var scores = new ArrayList<Long>();
        for (var line : incompleteLines) {
            scores.add(calculateIncompleteScore(line));
        }
        scores.sort(Long::compare);

        return scores.get((scores.size() - 1) / 2);
    }

    public static int calculateErrorScore(String line) {
        var stack = new Stack<Character>();

        for (var c : line.toCharArray()) {
            if (isOpenBrace(c)) {
                stack.push(c);
            }
            if (isCloseBrace(c)) {
                var o = stack.pop();
                if (BRACE_MAP.get(o) != c) {
                    return ILLEGAL_POINTS_MAP.get(c);
                }
            }
        }

        return 0;
    }

    public static long calculateIncompleteScore(String line) throws Exception {
        var stack = new Stack<Character>();
        for (var c : line.toCharArray()) {
            if (isOpenBrace(c)) {
                stack.push(c);
            }

            if (isCloseBrace(c)) {
                var o = stack.pop();
                if (BRACE_MAP.get(o) != c) {
                    throw new Exception("Illegal.");
                }
            }
        }

        var score = 0L;

        while (stack.size() > 0) {
            var o = stack.pop();
            var c = BRACE_MAP.get(o);
            var points = INCOMPLETE_POINTS_MAP.get(c);

            score *= 5;
            score += points;
        }

        return score;
    }

    public static List<String> getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day10/input"));
        return lines;
    }
}
