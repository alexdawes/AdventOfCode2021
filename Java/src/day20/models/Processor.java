package day20.models;

public class Processor {
    private String algorithm;

    public Processor(String algorithm) {

        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Image process(Image image) {
        var width = image.getWidth();
        var height = image.getHeight();

        var result = new Character[height + 2][width + 2];

        for (var i = -1; i < width + 1; i++) {
            for (var j = -1; j < height + 1; j++) {
                var index = image.getNeighbourhoodIndex(i, j);
                result[j + 1][i + 1] = algorithm.charAt(index);
            }
        }

        var newInfinity = algorithm.charAt(image.getNeighbourhoodIndex(-100, -100));

        return new Image(result, newInfinity);
    }
}
