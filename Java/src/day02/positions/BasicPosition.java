package day02.positions;

public class BasicPosition {
    private Integer horizontal;
    private Integer depth;

    public BasicPosition(Integer horizontal, Integer depth) {

        this.horizontal = horizontal;
        this.depth = depth;
    }

    public Integer getHorizontal() { return horizontal; }
    public Integer getDepth() { return depth; }

    @Override
    public String toString() {
        return "BasicPosition{" +
                "horizontal=" + horizontal +
                ", depth=" + depth +
                '}';
    }
}
