package day14;

import day11.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var system = getInputs();
        var template = system.getTemplate();
        var rules = system.getRules();

        return solve(template, rules, 10);
    }

    public static long part2() throws Exception {
        var system = getInputs();
        var template = system.getTemplate();
        var rules = system.getRules();

        return solve(template, rules, 40);
    }

    public static long solve(String template, Map<String,String> rules, Integer count) {
        var pairs = getPairCounts(template);
        var first = template.charAt(0);
        var last = template.charAt(template.length() - 1);

        for (var i = 0; i < count; i++) {
            pairs = step(pairs, rules);
        }

        var characterCounts = new HashMap<Character, Long>();
        if (!characterCounts.containsKey(first)) {
            characterCounts.put(first, 0L);
        }
        if (!characterCounts.containsKey(last)) {
            characterCounts.put(last, 0L);
        }
        characterCounts.put(first, characterCounts.get(first) + 1);
        characterCounts.put(last, characterCounts.get(last) + 1);

        for (var pair : pairs.keySet()) {
            var c = pairs.get(pair);
            var f = pair.charAt(0);
            var l = pair.charAt(1);

            if (!characterCounts.containsKey(f)) {
                characterCounts.put(f, 0L);
            }
            if (!characterCounts.containsKey(l)) {
                characterCounts.put(l, 0L);
            }
            characterCounts.put(f, characterCounts.get(f) + c);
            characterCounts.put(l, characterCounts.get(l) + c);
        }

        var counts = new HashMap<Character, Long>();
        for (var k : characterCounts.keySet()) {
            counts.put(k, characterCounts.get(k) / 2);
        }

        return getScore(counts);
    }

    public static Map<String, Long> getPairCounts(String s) {
        var result = new HashMap<String, Long>();
        for (var i = 0; i < s.length() - 1; i++) {
            var pair = s.substring(i, i+2);
            if (!result.containsKey(pair)) {
                result.put(pair, 0L);
            }
            result.put(pair, result.get(pair) + 1);
        }
        return result;
    }

    public static Map<String, Long> step(Map<String, Long> pairs, Map<String, String> rules) {
        var result = new HashMap<String, Long>();

        for (var pair : pairs.keySet()) {
            var count = pairs.get(pair);

            if (!rules.containsKey(pair)) {
                if (!result.containsKey(pair)) {
                    result.put(pair, 0L);
                }
                result.put(pair, result.get(pair) + count);
            }

            var first = pair.charAt(0);
            var last = pair.charAt(1);
            var middle = rules.get(pair);

            var firstPair = first + middle;
            var secondPair = middle + last;

            if (!result.containsKey(firstPair)) {
                result.put(firstPair, 0L);
            }
            if (!result.containsKey(secondPair)) {
                result.put(secondPair, 0L);
            }

            result.put(firstPair, result.get(firstPair) + count);
            result.put(secondPair, result.get(secondPair) + count);
        }

        return result;
    }

    public static long getScore(Map<Character,Long> characterCounts) {
        Character mostCommon = null;
        Character leastCommon = null;

        for (var c : characterCounts.keySet()) {
            if (mostCommon == null || characterCounts.get(c) > characterCounts.get(mostCommon)) {
                mostCommon = c;
            }
            if (leastCommon == null || characterCounts.get(c) < characterCounts.get(leastCommon)) {
                leastCommon = c;
            }
        }

        return characterCounts.get(mostCommon) - characterCounts.get(leastCommon);
    }

    public static PolymerSystem getInputs() throws IOException {
        var lines = Files.readAllLines(Path.of("src/day14/input"));

        var template = lines.get(0);
        var rulesList = lines.stream().skip(2).toList();

        var rules = new HashMap<String, String>();
        for(var r : rulesList) {
            var split = r.split(" -> ");
            rules.put(split[0], split[1]);
        }

        return new PolymerSystem(template, rules);
    }
}
