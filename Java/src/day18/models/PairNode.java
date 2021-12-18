package day18.models;

public class PairNode extends Node {
    private Node left;
    private Node right;

    public PairNode(Node left, Node right, PairNode parent) {
        super(parent);
        this.setLeft(left);
        this.setRight(right);
    }

    public PairNode(Node left, Node right) {
        this(left, right, null);
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        this.left.setParent(this);
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        this.right.setParent(this);
    }

    @Override
    public String toString() {
        return "[" + left.toString() + "," + right.toString() + "]";
    }

    @Override
    public int getMagnitude() {
        return 3 * left.getMagnitude() + 2 * right.getMagnitude();
    }

    @Override
    public Node clone() {
        return new PairNode(left.clone(), right.clone());
    }

    @Override
    public boolean checkExplodes() throws Exception {
        if (getLevel() >= 4) {
            explode();
            return true;
        }

        return left.checkExplodes() || right.checkExplodes();
    }

    @Override
    public boolean checkSplits() throws Exception {
        return left.checkSplits() || right.checkSplits();
    }

    public void explode() throws Exception {
        if (left instanceof PairNode) {
            ((PairNode) left).explode();
        }

        if (right instanceof PairNode) {
            ((PairNode) right).explode();
        }

        var leftValue = ((ValueNode) left).getValue();
        var rightValue = ((ValueNode) right).getValue();

        if (parent != null) {
            parent.explodeLeft(leftValue, this);
            parent.explodeRight(rightValue, this);

            if (parent.getRight() == this) {
                parent.setRight(new ValueNode(0));
            }
            if (parent.getLeft() == this) {
                parent.setLeft(new ValueNode(0));
            }
        }
    }

    private void explodeLeft(int value, PairNode from) {
        if (from == right) {
            left.acceptLeft(value);
        } else if (parent != null) {
            parent.explodeLeft(value, this);
        }
    }

    private void explodeRight(int value, PairNode from) {
        if (from == left) {
            right.acceptRight(value);
        } else if (parent != null) {
            parent.explodeRight(value, this);
        }
    }

    @Override
    public void acceptLeft(int value) {
        right.acceptLeft(value);
    }

    @Override
    public void acceptRight(int value) {
        left.acceptRight(value);
    }
}
