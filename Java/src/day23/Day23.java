package day23;

import day22.models.Cuboid;
import day22.models.Instruction;
import day22.models.InstructionType;
import day22.models.Range;
import day23.models.Burrow;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day23 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var burrow = getInput();
        return burrow.solveLeastEnergy();
    }

    public static long part2() throws Exception {
        return 0;
    }

    public static Burrow getInput() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day23/input"));
        return Burrow.parse(lines);
    }
}
