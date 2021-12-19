package day19.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Matrix {
    private int[][] values;
    private int width;
    private int height;

    public Matrix(int[][] values) {
        width = values[0].length;
        height = values.length;
        this.values = values;
    }

    public int get(int i, int j) {
        return values[j][i];
    }

    public Vector getColumn(int i) {
        var result = new int[height];
        for (var j = 0; j < height; j++) {
            result[j] = get(i, j);
        }
        return new Vector(result);
    }

    public Vector getRow(int j) {
        var result = new int[width];
        for (var i = 0; i < width; i++) {
            result[i] = get(i, j);
        }
        return new Vector(result);
    }

    public Matrix multiply(Matrix other) {
        var result = new int[height][other.width];
        for (var i = 0; i < other.width; i++) {
            for (var j = 0; j < height; j++) {
                var row = getRow(j);
                var column = other.getColumn(i);
                var value = row.dot(column);
                result[j][i] = value;
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose() {
        var matrix = new int[width][height];
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                matrix[i][j] = values[j][i];
            }
        }
        return new Matrix(matrix);
    }

    public Matrix clone() {
        var newValues = new int [height][width];
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                newValues[j][i] = values[j][i];
            }
        }
        return new Matrix(newValues);
    }

    public Vector transform(Vector v) {
        return v.transform(this);
    }

    @Override
    public String toString() {
        var rows = new ArrayList<String>();
        for (var j = 0; j < height; j++) {
            var vals = new ArrayList<String>();
            for (var i = 0; i < width; i++) {
                var v = values[j][i];
                var s = ((Integer)v).toString();
                vals.add(s);
            }
            rows.add("(" + String.join(",", vals) + ")");
        }
        return "(" + String.join(",", rows) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Matrix) {
            var m = (Matrix)o;
            if (m.width == width && m.height == height) {
                for (var i = 0; i < width; i++) {
                    for (var j = 0; j < height; j++) {
                        if (get(i, j) != m.get(i, j)) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                hash = 31 * hash + get(i, j);
            }
        }
        return hash;
    }
}
