package day15;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Coordinate &&
                ((Coordinate)o).getX() == x &&
                ((Coordinate)o).getY() == y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
