package day03;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Day03 {
    public static void main(String[] args) throws Exception {
        var p1 = part1();
        System.out.println("Part 1: " + p1);

        var p2 = part2();
        System.out.println("Part 2: " + p2);
    }

    public static int part1() throws Exception {
        var strings = GetBinaryStrings();

        var gamma = GetGammaRate(strings);
        var epsilon = GetEpsilonRate(strings);

        return gamma * epsilon;
    }

    public static int part2() throws Exception {
        var strings = GetBinaryStrings();

        var oxygenGeneratorRating = GetOxygenGeneratorRating(strings);
        var co2ScrubberRating = GetCO2ScrubberRating(strings);

        return oxygenGeneratorRating * co2ScrubberRating;
    }

    public static int GetOxygenGeneratorRating(List<String> strings) {
        List<String> inputs = new ArrayList<String>(strings);

        var position = 0;
        while(inputs.size() != 1) {
            var mostCommon = GetMostCommonBits(inputs).charAt(position);
            int finalPosition = position;
            inputs = inputs.stream().filter(s -> s.charAt(finalPosition) == mostCommon).toList();
            position++;
        }

        var chosen = inputs.get(0);
        return Integer.parseInt(chosen, 2);
    }

    public static int GetCO2ScrubberRating(List<String> strings) {
        List<String> inputs = new ArrayList<String>(strings);

        var position = 0;
        while(inputs.size() != 1) {
            var leastCommon = GetLeastCommonBits(inputs).charAt(position);
            int finalPosition = position;
            inputs = inputs.stream().filter(s -> s.charAt(finalPosition) == leastCommon).toList();
            position++;
        }

        var chosen = inputs.get(0);
        return Integer.parseInt(chosen, 2);
    }

    public static int GetGammaRate(List<String> strings) {
        var gammaRateString = GetMostCommonBits(strings);

        var gammaRate = Integer.parseInt(gammaRateString, 2);
        return gammaRate;
    }

    public static int GetEpsilonRate(List<String> strings) {
        var epsilonRateString = GetLeastCommonBits(strings);

        var epsilonRate = Integer.parseInt(epsilonRateString, 2);
        return epsilonRate;
    }

    public static String GetLeastCommonBits(List<String> strings) {
        var mostCommonBits = GetMostCommonBits(strings);
        var builder = new StringBuilder();

        for (var i = 0; i < mostCommonBits.length(); i++) {
            var c = mostCommonBits.charAt(i);
            builder.append(c == '0' ? '1' : '0');
        }

        return builder.toString();
    }

    public static String GetMostCommonBits(List<String> strings) {
        var length = strings.get(0).length();

        StringBuilder builder = new StringBuilder();
        for (var i = 0; i < length; i++) {
            var values = new ArrayList<Character>();
            for (var s : strings) { values.add(s.charAt(i)); }

            var zeros = values.stream().filter(v -> v == '0').count();
            var ones = values.stream().filter(v -> v == '1').count();

            builder.append(zeros > ones ? '0' : '1');
        }

        return builder.toString();
    }


    public static List<String> GetBinaryStrings() throws Exception {
        var lines = Files.readAllLines(Path.of("src/day03/input"));
        return lines;
    }
}
