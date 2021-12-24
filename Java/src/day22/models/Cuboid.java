package day22.models;

import java.util.ArrayList;
import java.util.List;

public class Cuboid {
    private Range xRange;
    private Range yRange;
    private Range zRange;

    public Cuboid(Range xRange, Range yRange, Range zRange) {
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
    }

    public Range getXRange() { return xRange; }
    public Range getYRange() { return yRange; }
    public Range getZRange() { return zRange; }

    public boolean intersects(Cuboid other) {
        return xRange.intersects(other.xRange) &&
                yRange.intersects(other.yRange) &&
                zRange.intersects(other.zRange);
    }

    public Cuboid getIntersection(Cuboid other) {
        if (!intersects(other)) {
            return null;
        }

        return new Cuboid(
                xRange.getIntersection(other.xRange),
                yRange.getIntersection(other.yRange),
                zRange.getIntersection(other.zRange));
    }

    public long getIntersectionSize(List<Cuboid> others) {
        if (others.size() == 0) {
            return 0;
        }

        long total = 0;
        for (var i = 0; i < others.size(); i++) {
            var cube = others.get(i);
            var rest = others.stream().limit(i).toList();
            var intersection = this.getIntersection(cube);
            if (intersection != null) {
                total += intersection.getExceptSize(rest);
            }
        }
        return total;
    }

    public long getExceptSize(List<Cuboid> others) {
        return getSize() - getIntersectionSize(others);
    }

    public long getSize() {
        return xRange.getSize() * yRange.getSize() * zRange.getSize();
    }

    @Override
    public String toString() {
        return "x=" + xRange + ",y=" + yRange + ",z=" + zRange;
    }
}
