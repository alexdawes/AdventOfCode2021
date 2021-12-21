package day20.models;

import java.util.List;

public class Data {
    private Image image;
    private String algorithm;

    public Data(Image image, String algorithm) {

        this.image = image;
        this.algorithm = algorithm;
    }

    public Image getImage() {
        return image;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public static Data parse(List<String> lines) {
        var algorithm = lines.get(0).trim();
        var rest = lines.stream().skip(2).toList();

        var height = rest.size();
        var width = rest.get(0).length();

        var image = new Character[height][width];
        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                image[j][i] = rest.get(j).charAt(i);
            }
        }

        return new Data(new Image(image, '.'), algorithm);
    }
}
