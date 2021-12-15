package day13;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int i, int j) {
        this.x = i;
        this.y = j;
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
