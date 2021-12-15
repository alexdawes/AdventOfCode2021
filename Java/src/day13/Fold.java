package day13;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Fold {
    private FoldType type;
    private int value;

    public Fold(FoldType type, int value) {
        this.type = type;
        this.value = value;
    }

    public FoldType getType() { return type; }
    public int getValue() { return value; }

    public Page Fold(Page page) throws Exception {
        var newPage = new HashSet<Coordinate>();

        for (var dot : page.getDots()) {
            var x = dot.getX();
            var y = dot.getY();
            switch (type) {
                case Horizontal: {
                    var newX = x > value ? value - (x - value) : x;
                    newPage.add(new Coordinate(newX, y));
                    break;
                }
                case Vertical: {
                    var newY = y > value ? value - (y - value) : y;
                    newPage.add(new Coordinate(x, newY));
                    break;
                }
                default:
                    throw new Exception("Unrecognised type");
            }
        }

        return new Page(newPage);
    }

    @Override
    public String toString() {
        return "fold along " + (type == FoldType.Horizontal ? 'x' : 'y') + "=" + value;
    }

    public static Fold Parse(String line) throws Exception {
        var pattern = Pattern.compile("^fold along (?<type>(x|y))=(?<value>\\d+)$");
        var matcher = pattern.matcher(line);

        if (!matcher.matches()) {
            throw new Exception(line + " is not valid.");
        };

        var type = matcher.group("type").equals("x") ? FoldType.Horizontal : FoldType.Vertical;
        var value = Integer.parseInt(matcher.group("value"));

        return new Fold(type, value);
    }
}

