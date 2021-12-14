package day08;

import java.util.*;

public class Line {
    private String[] signalPatterns;
    private String[] outputPatterns;

    public Line(String[] signalPatterns, String[] outputPatterns) {
        this.signalPatterns = signalPatterns;
        this.outputPatterns = outputPatterns;
    }

    public String[] getSignalPatterns() { return signalPatterns; };
    public String getSignalPattern(int index) { return signalPatterns[index]; }
    public String[] getOutputPatterns() { return outputPatterns; }
    public String getOutputPattern(int index) { return outputPatterns[index]; }

    public int solveOutput() {
        var one = Arrays.stream(signalPatterns).filter(s -> s.length() == 2).findFirst().get();
        var seven = Arrays.stream(signalPatterns).filter(s -> s.length() == 3).findFirst().get();
        var four = Arrays.stream(signalPatterns).filter(s -> s.length() == 4).findFirst().get();
        var eight = Arrays.stream(signalPatterns).filter(s -> s.length() == 7).findFirst().get();

        var zeroSixOrNine = Arrays.stream(signalPatterns).filter(s -> s.length() == 6).toList();
        var twoThreeOrFive = Arrays.stream(signalPatterns).filter(s -> s.length() == 5).toList();

        var cf = StrToCharList(one);
        var acf = StrToCharList(seven);
        var bcdf = StrToCharList(four);
        var abcdefg = StrToCharList(eight);

        var a = acf.stream().filter(s -> !cf.contains(s)).toList().get(0);

        var adg = StrToCharList(twoThreeOrFive.get(0)).stream()
                .filter(StrToCharList(twoThreeOrFive.get(1))::contains)
                .filter(StrToCharList(twoThreeOrFive.get(2))::contains)
                .toList();

        var dg = adg.stream().filter(s -> s != a).toList();

        var abfg = StrToCharList(zeroSixOrNine.get(0)).stream()
                .filter(StrToCharList(zeroSixOrNine.get(1))::contains)
                .filter(StrToCharList(zeroSixOrNine.get(2))::contains)
                .toList();

        var bfg = abfg.stream().filter(s -> s != a).toList();

        var bf = bfg.stream().filter(s -> !dg.contains(s)).toList();

        var f = bf.stream().filter(acf::contains).toList().get(0);
        var b = bf.stream().filter(s -> s != f).toList().get(0);
        var g = bfg.stream().filter(s -> !bf.contains(s)).toList().get(0);
        var d = dg.stream().filter(s -> s != g).toList().get(0);
        var c = acf.stream().filter(s -> s != a && s != f).toList().get(0);
        var e = abcdefg.stream().filter(s -> s != a && s != b && s != c && s != d && s != f && s != g).toList().get(0);

        var charMap = new HashMap<Character, Character>();
        charMap.put(a, 'a');
        charMap.put(b, 'b');
        charMap.put(c, 'c');
        charMap.put(d, 'd');
        charMap.put(e, 'e');
        charMap.put(f, 'f');
        charMap.put(g, 'g');

        var stringMap = new HashMap<String, String>();
        for (var signalPattern: signalPatterns) {
            var charArray = signalPattern.toCharArray();
            var mappedCharArray = new char[charArray.length];
            for (var i = 0; i < charArray.length; i++) {
                mappedCharArray[i] = charMap.get(charArray[i]);
            }
            Arrays.sort(mappedCharArray);
            stringMap.put(signalPattern, new String(mappedCharArray));
        }

        var numberMap = new HashMap<String, String>();
        numberMap.put("abcefg", "0");
        numberMap.put("cf", "1");
        numberMap.put("acdeg", "2");
        numberMap.put("acdfg", "3");
        numberMap.put("bcdf", "4");
        numberMap.put("abdfg", "5");
        numberMap.put("abdefg", "6");
        numberMap.put("acf", "7");
        numberMap.put("abcdefg", "8");
        numberMap.put("abcdfg", "9");

        var number = Integer.parseInt(numberMap.get(stringMap.get(getOutputPattern(0))) +
                numberMap.get(stringMap.get(getOutputPattern(1))) +
                numberMap.get(stringMap.get(getOutputPattern(2))) +
                numberMap.get(stringMap.get(getOutputPattern(3))));

        return number;
    }

    public static List<Character> StrToCharList (String s) {
        return s.chars().mapToObj(c -> (char)c).toList();
    }

    public static Line Parse(String line) {
        var split = line.split("\\|");
        var signalPatterns = split[0].trim().split(" ");
        var outputPatterns = split[1].trim().split(" ");

        String[] sortedSignalPatterns = new String[signalPatterns.length];
        for(var i = 0; i < signalPatterns.length; i++) {
            var s = signalPatterns[i];
            var cArr = s.toCharArray();
            Arrays.sort(cArr);
            sortedSignalPatterns[i] = new String(cArr);
        }

        String[] sortedOutputPatterns = new String[outputPatterns.length];
        for(var i = 0; i < outputPatterns.length; i++) {
            var s = outputPatterns[i];
            var cArr = s.toCharArray();
            Arrays.sort(cArr);
            sortedOutputPatterns[i] = new String(cArr);
        }
        return new Line(sortedSignalPatterns, sortedOutputPatterns);
    }
}
