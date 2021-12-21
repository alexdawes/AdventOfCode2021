package day21.models.dice;

public class DiracDie {
    public int[] roll() {
        int[] result = { 1, 2, 3 };
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DiracDie;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static DiracDie Instance = new DiracDie();
}
