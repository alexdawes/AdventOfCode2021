package day05;

import java.util.ArrayList;
import java.util.List;

public class LineSegment {
    private Coordinate start;
    private Coordinate end;

    public LineSegment(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Coordinate getStart() { return start; }
    public Coordinate getEnd() { return end; }
    public boolean isHorizontalOrVertical() { return isHorizontal() || isVertical(); }
    public boolean isHorizontal() { return start.getY() == end.getY(); }
    public boolean isVertical() { return start.getX() == end.getX(); }

    public List<Coordinate> GetCoordinates() {
        var x1 = start.getX();
        var y1 = start.getY();
        var x2 = end.getX();
        var y2  = end.getY();

        var x = x1;
        var y = y1;

        var result = new ArrayList<Coordinate>();
        result.add(new Coordinate(x, y));

        while (x != x2 || y != y2) {
            if (x < x2) { x++; }
            if (x > x2) { x--; }
            if (y < y2) { y++; }
            if (y > y2) { y--; }
            result.add(new Coordinate(x, y));
        }

        return result;
    }

    public static LineSegment Parse(String line) {
        var split = line.split(" -> ");
        var first = split[0].split(",");
        var second = split[1].split(",");

        var start = new Coordinate(
                Integer.parseInt(first[0]),
                Integer.parseInt(first[1]));
        var end = new Coordinate(
                Integer.parseInt(second[0]),
                Integer.parseInt(second[1]));

        return new LineSegment(start, end);
    }
}
