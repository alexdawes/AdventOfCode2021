package day20;

import day20.models.Data;
import day20.models.Processor;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day20 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static long part1() throws Exception {
        var data = getInput();
        var image = data.getImage();
        var processor = new Processor(data.getAlgorithm());

        for (var i = 0; i < 2; i++) {
            image = processor.process(image);
        }

        return image.countPixels();
    }

    public static long part2() throws Exception {
        var data = getInput();
        var image = data.getImage();
        var processor = new Processor(data.getAlgorithm());

        for (var i = 0; i < 50; i++) {
            image = processor.process(image);
        }

        return image.countPixels();
    }
    public static Data getInput() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day20/input"));
        return Data.parse(lines);
    }
}
