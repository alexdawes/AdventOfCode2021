package day21.models.dice;

public class DeterministicDie {
    private int nextValue = 1;
    private int rollCount = 0;
    public int roll() {
        var next = nextValue;
        nextValue = nextValue < 100 ? nextValue + 1 : 1;
        rollCount++;
        return next;
    }

    @Override
    public boolean equals (Object o) {
        return o instanceof DeterministicDie &&
                ((DeterministicDie)o).nextValue == nextValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + nextValue;
        return hash;
    }

    public int getRollCount() {
        return rollCount;
    }
}
