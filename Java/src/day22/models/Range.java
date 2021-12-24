package day22.models;

public class Range {
    private long min;
    private long max;

    public Range(long min, long max) {
        if (max < min) {
            var _min = max;
            max = min;
            min = _min;
        }
        this.min = min;
        this.max = max;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public boolean contains(long value) {
        return min <= value && value <= max;
    }

    public boolean intersects(Range other) {
        return other.min <= max && min <= other.max;
    }

    public Range getIntersection(Range other) {
        if (!intersects(other)) {
            return null;
        }
        return new Range(
                Math.max(min, other.min),
                Math.min(max, other.max));
    }

    public long getSize() {
        return max - min + 1;
    }

    @Override
    public String toString() {
        return min + ".." + max;
    }
}
