package day24;

public class Relationship {
    private int x1;
    private int x2;
    private int diff;

    public Relationship(int x1, int x2, int diff) {
        this.x1 = x1;
        this.x2 = x2;
        this.diff = diff;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getDiff() {
        return diff;
    }
}
