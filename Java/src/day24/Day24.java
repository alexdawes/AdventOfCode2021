package day24;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class Day24 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var lines = getInput();
        var triads = getTriads(lines);
        var relationships = getRelationships(triads);

        var result = new int[14];
        for (var relationship : relationships) {
            var x1 = relationship.getX1();
            var x2 = relationship.getX2();
            var diff = relationship.getDiff();

            // System.out.println("X" + x1 + " " + (diff > 0 ? "+" : "-") + " " + Math.abs(diff) + " = X" + x2);

            if (diff <= 0) {
                result[x1] = 9;
                result[x2] = 9 + diff;
            } else {
                result[x1] = 9 - diff;
                result[x2] = 9;
            }
        }

        var sb = new StringBuilder();
        for (var i : result) {
            sb.append(i);
        }
        return Long.parseLong(sb.toString());
    }

    public static long part2() throws Exception {
        var lines = getInput();
        var triads = getTriads(lines);
        var relationships = getRelationships(triads);

        var result = new int[14];
        for (var relationship : relationships) {
            var x1 = relationship.getX1();
            var x2 = relationship.getX2();
            var diff = relationship.getDiff();

            // System.out.println("X" + x1 + " " + (diff > 0 ? "+" : "-") + " " + Math.abs(diff) + " = X" + x2);

            if (diff >= 0) {
                result[x1] = 1;
                result[x2] = 1 + diff;
            } else {
                result[x1] = 1 - diff;
                result[x2] = 1;
            }
        }

        var sb = new StringBuilder();
        for (var i : result) {
            sb.append(i);
        }
        return Long.parseLong(sb.toString());
    }

    public static List<Relationship> getRelationships(List<Triad> triads) {
        var relationships = new ArrayList<Relationship>();

        var stack = new Stack<Integer>();
        for (var idx = 0; idx < triads.size(); idx++) {
            var triad = triads.get(idx);
            var a = triad.getA();
            if (a == 1) {
                stack.push(idx);
            }
            else if (a == 26) {
                var prevIdx = stack.pop();
                var prevTriad = triads.get(prevIdx);

                relationships.add(new Relationship(prevIdx, idx, prevTriad.getC() + triad.getB()));
            }
        }

        return relationships;
    }

    public static List<Triad> getTriads(List<String> lines) throws Exception {
        var triads = new ArrayList<Triad>();
        for (var i = 0; i < 14; i++) {
            var batch = lines.stream().skip(18 * i).limit(18).toList();
            var line4 = batch.get(4);
            var line5 = batch.get(5);
            var line15 = batch.get(15);

            var patt4 = Pattern.compile("div z (?<a>-?\\d+)");
            var patt5 = Pattern.compile("add x (?<b>-?\\d+)");
            var patt15 = Pattern.compile("add y (?<c>-?\\d+)");

            var matcher4 = patt4.matcher(line4);
            var matcher5 = patt5.matcher(line5);
            var matcher15 = patt15.matcher(line15);

            if (!matcher4.matches() || !matcher5.matches() || !matcher15.matches()) {
                throw new Exception("Something went wrong.");
            }

            var a = Integer.parseInt(matcher4.group("a"));
            var b = Integer.parseInt(matcher5.group("b"));
            var c = Integer.parseInt(matcher15.group("c"));

            triads.add(new Triad(a, b, c));
        }

        return triads;
    }

    public static List<String> getInput() throws Exception {
        return Files.readAllLines(Path.of("src/day24/input"));
    }
}
