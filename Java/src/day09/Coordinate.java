package day09;

public class Coordinate {
    private int i;
    private int j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() { return i; }
    public int getJ() { return j; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + i;
        hash = 31 * hash + j;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Coordinate &&
                ((Coordinate)o).getI() == i &&
                ((Coordinate)o).getJ() == j);
    }

    @Override
    public String toString() {
        return "(" + i + "," + j + ")";
    }

}
