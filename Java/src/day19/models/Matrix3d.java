package day19.models;

import java.util.HashSet;
import java.util.Set;

public class Matrix3d {

    public static Matrix getXRotation() {
        int[][] matrix = { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 1, 0 } };
        return new Matrix(matrix);
    }

    public static Matrix getYRotation() {
        int[][] matrix = { { 0, 0, -1 }, { 0, 1, 0 }, { 1, 0, 0 }};
        return new Matrix(matrix);
    }

    public static Matrix getZRotation() {
        int[][] matrix= { { 0, -1, 0 }, { 1, 0, 0 }, { 0, 0, 1 }};
        return new Matrix(matrix);
    }

    public static Set<Matrix> getAllRotations() {
        var xRot = getXRotation();
        var yRot = getYRotation();
        var zRot = getZRotation();

        int[][] id = {{1,0,0},{0,1,0},{0,0,1}};
        var matrix = new Matrix(id);

        var result = new HashSet<Matrix>();
        result.add(matrix);

        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                for (var k = 0; k < 4; k++) {
                    matrix = matrix.multiply(zRot);
                    result.add(matrix);
                }
                matrix = matrix.multiply(yRot);
                result.add(matrix);
            }
            matrix = matrix.multiply(xRot);
            result.add(matrix);
        }

        return result;
    }
}
