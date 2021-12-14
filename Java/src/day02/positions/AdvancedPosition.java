package day02.positions;

public class AdvancedPosition extends BasicPosition {

    private Integer aim;

    public AdvancedPosition(Integer horizontal, Integer depth, Integer aim) {
        super(horizontal, depth);
        this.aim = aim;
    }

    public Integer getAim() { return aim; }

    @Override
    public String toString() {
        return "AdvancedPosition{" +
                "horizontal=" + getHorizontal() + ", " +
                "depth=" + getDepth() + ", " +
                "aim=" + aim +
                '}';
    }
}
