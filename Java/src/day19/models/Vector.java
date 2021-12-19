package day19.models;

import java.util.ArrayList;

public class Vector {
    private int[] values;
    private int length;

    public Vector (int... values) {
        length = values.length;
        this.values = values;
    }

    public int get(int i) {
        return values[i];
    }

    public Matrix toMatrix() {
        var matrix = new int[length][1];
        for (var i = 0; i < length; i++) {
            matrix[i][0] = values[i];
        }
        return new Matrix(matrix);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (var val : values) {
            hash = 31 * hash + val;
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector) {
            var v = (Vector)o;
            if (length != v.length) {
                return false;
            }
            for (var i = 0; i < length; i++) {
                if (values[i] != v.values[i]) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        var lst = new ArrayList<String>();
        for (var i = 0; i < length; i++) {
            lst.add(((Integer)values[i]).toString());
        }
        return "(" + String.join(",", lst) + ")";
    }

    public int distanceTo(Vector other) {
        var sum = 0;
        for (var i = 0; i < length; i++) {
            sum += Math.abs(values[i] - other.values[i]);
        }
        return sum;
    }

    public Vector add(Vector other) {
        var arr = new int[length];
        for (var i = 0; i < length; i++) {
            arr[i] = values[i] + other.values[i];
        }
        return new Vector(arr);
    }

    public Vector subtract(Vector other) {
        var arr = new int[length];
        for (var i = 0; i < length; i++) {
            arr[i] = values[i] - other.values[i];
        }
        return new Vector(arr);
    }

    public int dot(Vector other) {
        var sum = 0;
        for (var i = 0; i < length; i++) {
            sum += values[i] * other.values[i];
        }
        return sum;
    }

    public Vector transform(Matrix matrix) {
        var vMatrix = toMatrix();
        var transformed = matrix.multiply(vMatrix);
        return transformed.getColumn(0);
    }

    public static Vector getTranslationVector(int... values) {
        return new Vector(values);
    }
}
