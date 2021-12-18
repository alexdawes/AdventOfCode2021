package day18.models;

import java.lang.reflect.ParameterizedType;

public class ValueNode extends Node {
    private int value;

    public ValueNode(int value, PairNode parent) {
        super(parent);
        this.value = value;
    }

    public ValueNode(int value) {
        this(value, null);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ((Integer) value).toString();
    }

    @Override
    public int getMagnitude() {
        return value;
    }

    @Override
    public Node clone() {
        return new ValueNode(value);
    }

    @Override
    public boolean checkSplits() {
        if (value >= 10) {
            split();
            return true;
        }
        return false;
    }

    public void split() {
        var leftValue = value / 2;
        var rightValue = value / 2 + (value % 2 == 0 ? 0 : 1);
        var pairNode = new PairNode(new ValueNode(leftValue), new ValueNode(rightValue));

        if (this.parent != null) {
            if (this.parent.getLeft() == this) {
                this.parent.setLeft(pairNode);
            }
            if (this.parent.getRight() == this) {
                this.parent.setRight(pairNode);
            }
        }
    }

    @Override
    public void acceptLeft(int value) {
        accept(value);
    }

    @Override
    public void acceptRight(int value) {
        accept(value);
    }

    private void accept(int value) {
        this.value += value;
    }
}
