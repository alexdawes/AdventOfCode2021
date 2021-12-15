package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class Day13 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: \n" + p2);
    }

    public static long part1() throws Exception {
        var instructions = getInputs();

        var page = instructions.getPage();
        var folds = instructions.getFolds();

        var fold = folds.get(0);
        page = fold.Fold(page);

        return page.getDots().size();
    }

    public static String part2() throws Exception {
        var instructions = getInputs();

        var page = instructions.getPage();
        var folds = instructions.getFolds();

        for (var fold : folds) {
            page = fold.Fold(page);
        }

        return page.toString();
    }

    public static Instructions getInputs() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day13/input"));

        var processingFolds = false;

        var page = new HashSet<Coordinate>();
        var folds = new ArrayList<Fold>();

        for (var line : lines) {
            if (line.isBlank()) {
                processingFolds = true;
                continue;
            }

            if (!processingFolds) {
                var split = line.split(",");
                var x = Integer.parseInt(split[0]);
                var y = Integer.parseInt(split[1]);
                page.add(new Coordinate(x, y));
                continue;
            }

            if (processingFolds) {
                var fold = Fold.Parse(line);
                folds.add(fold);
            }
        }
        return new Instructions(new Page(page), folds);
    }
}
