package day17;

public class Target {
    private Range xRange;
    private Range yRange;

    public Target(Range xRange, Range yRange) {
        this.xRange = xRange;
        this.yRange = yRange;
    }

    public Range getXRange() {
        return xRange;
    }

    public Range getYRange() {
        return yRange;
    }

    public boolean contains(Coordinate coord) {
        return xRange.contains(coord.getX()) && yRange.contains(coord.getY());
    }
}
