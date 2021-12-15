package day13;

import java.util.Set;

public class Page {
    private Set<Coordinate> dots;

    public Page(Set<Coordinate> dots) {
        this.dots = dots;
    }

    public Set<Coordinate> getDots() { return dots; }

    @Override
    public String toString() {
        var minX = dots.stream().map(c -> c.getX()).min(Integer::compare).get();
        var maxX = dots.stream().map(c -> c.getX()).max(Integer::compare).get();
        var minY = dots.stream().map(c -> c.getY()).min(Integer::compare).get();
        var maxY = dots.stream().map(c -> c.getY()).max(Integer::compare).get();

        var builder = new StringBuilder();

        for (var j = minY; j <= maxY; j++) {
            var lineBuilder = new StringBuilder();
            for (var i = minX; i <= maxX; i++) {
                var character = getDots().contains(new Coordinate(i, j))
                        ? '#'
                        : '.';
                lineBuilder.append(character);
            }
            builder.append(lineBuilder.toString() + "\n");
        }

        return builder.toString();
    }
}
