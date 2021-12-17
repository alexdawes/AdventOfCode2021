package day17;

public class Range {
    private Integer min;
    private Integer max;

    public Range(Integer min, Integer max) throws Exception {
        this.min = min;
        this.max = max;

        if (min > max) {
            throw new Exception("Invalid range");
        }
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public boolean contains(Integer value) {
        return min <= value && value <= max;
    }
}
