package day22;

import day22.models.Cuboid;
import day22.models.Instruction;
import day22.models.InstructionType;
import day22.models.Range;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day22 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var instructions = getInput();
        var boundingCuboid = new Cuboid(new Range(-50, 50), new Range(-50, 50), new Range(-50, 50));
        var boundedInstructions = new ArrayList<Instruction>();
        for (var inst : instructions) {
            var boundedCuboid = inst.getCuboid().getIntersection(boundingCuboid);

            if (boundedCuboid != null) {
                var boundedInst = new Instruction(inst.getType(), boundedCuboid);
                boundedInstructions.add(boundedInst);
            }
        }

        return countOn(boundedInstructions);
    }

    public static long part2() throws Exception {
        var instructions = getInput();
        return countOn(instructions);
    }

    public static long countOn(List<Instruction> instructions) {
        long countOn = 0;
        for (var i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            if (instruction.getType() == InstructionType.On) {
                var rest = instructions.stream().skip(i + 1).map(inst -> inst.getCuboid()).toList();
                var size = instruction.getCuboid().getSize();
                var intSize = instruction.getCuboid().getIntersectionSize(rest);
                countOn += (size - intSize);
            }
        }
        return countOn;
    }

    public static List<Instruction> getInput() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day22/input"));
        return lines.stream().map(l -> Instruction.parse(l)).toList();
    }
}
