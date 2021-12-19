package day19.models;

public class Pair<T> {
    private T left;
    private T right;

    public Pair(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() { return left; }
    public T getRight() { return right; }

    @Override
    public boolean equals(Object other) {
        return other instanceof Pair<?> &&
                ((Pair<?>)other).left.equals(left) &&
                ((Pair<?>)other).right.equals(right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * right.hashCode();
    }
}
