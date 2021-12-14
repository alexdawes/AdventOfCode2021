package day02;

import day02.instructions.Instruction;
import day02.positions.AdvancedPosition;
import day02.positions.BasicPosition;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static Integer part1() throws Exception {
        var instructions = GetInstructions();

        var position = new BasicPosition(0, 0);

        for (var inst : instructions) {
            position = inst.Iterate(position);
        }

        return position.getHorizontal() * position.getDepth();
    }

    public static Integer part2() throws Exception {
        var instructions = GetInstructions();

        var position = new AdvancedPosition(0, 0, 0);

        for (var inst : instructions) {
            position = inst.Iterate(position);
        }

        return position.getHorizontal() * position.getDepth();
    }

    public static List<Instruction> GetInstructions() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day02/input"));
        var instructions = new ArrayList<Instruction>();
        for (var line : lines) {
            instructions.add(Instruction.Parse(line));
        }
        return instructions;
    }
}
