package day18;

import day17.Coordinate;
import day17.Trench;
import day18.models.Node;
import day18.models.NodeReader;
import day18.models.PairNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var nodes = getNodes();

        var sum = nodes.get(0);
        for (var node : nodes.stream().skip(1).toList()) {
            sum = Add(sum, node);
        }

        return sum.getMagnitude();
    }

    public static long part2() throws Exception {
        var nodes = getNodes();
        var magnitudes = new ArrayList<Integer>();

        for (var i = 0; i < nodes.size(); i++) {
            for (var j = 0; j < nodes.size(); j++) {
                if (i != j) {
                    var left = nodes.get(i).clone();
                    var right = nodes.get(j).clone();
                    var sum = Add(left, right);
                    var magnitude = sum.getMagnitude();
                    magnitudes.add(magnitude);
                }
            }
        }

        return magnitudes.stream().max(Integer::compare).get();
    }

    private static Node Add(Node left, Node right) throws Exception {
        var sum = new PairNode(left, right);

        while (true) {
            if (!sum.checkExplodes() && !sum.checkSplits()) {
                break;
            }
        }

        return sum;
    }

    public static List<Node> getNodes() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day18/input"));
        return lines.stream().map(l -> {
            var reader = new NodeReader(l);
            var node = reader.readNode();
            return node;
        }).toList();
    }
}
