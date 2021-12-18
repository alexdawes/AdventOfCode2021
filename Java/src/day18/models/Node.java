package day18.models;

public abstract class Node {
    protected PairNode parent;

    public Node(PairNode parent) {
        this.parent = parent;
    }

    public PairNode getParent() {
        return parent;
    }

    public void setParent(PairNode parent) {
        this.parent = parent;
    }

    public abstract int getMagnitude();

    public int getLevel() {
        if (parent == null) {
            return 0;
        }
        return parent.getLevel() + 1;
    }

    public abstract void acceptLeft(int value);

    public abstract void acceptRight(int value);

    public boolean checkExplodes() throws Exception {
        return false;
    }

    public boolean checkSplits() throws Exception {
        return false;
    }

    public abstract Node clone();
}
