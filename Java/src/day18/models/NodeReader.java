package day18.models;

public class NodeReader {
    private String line;
    private int pointer = 0;

    public NodeReader(String line) {
        this.line = line;
    }

    private char peek() {
        return line.charAt(pointer);
    }

    private char next() {
        var next = peek();
        pointer++;
        return next;
    }

    private void skip() {
        pointer++;
    }

    public Node readNode() {
        var next = next();
        if (next == '[') {
            var left = readNode();
            skip();
            var right = readNode();
            skip();
            return new PairNode(left, right);
        } else {
            var value = Character.getNumericValue(next);
            return new ValueNode(value);
        }
    }
}
