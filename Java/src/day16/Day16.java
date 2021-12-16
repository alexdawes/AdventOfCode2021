package day16;

import day15.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class Day16 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var packets = getInputs();

        return packets.stream().map(p -> p.getTotalVersionNumbers()).reduce(0, (a, b) -> a + b);
    }

    public static long part2() throws Exception {
        var packets = getInputs();

        return packets.stream().map(p -> p.getValue()).reduce(0L, (a, b) -> a + b);
    }

    public static List<Packet> getInputs() throws IOException {
        var line = Files.readString(Path.of("src/day16/input"));
        return Packet.ParseHex(line);
    }
}
