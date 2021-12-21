package day20.models;

public class Image {
    private Character[][] pixels;
    private Character infinity;

    public Image(Character[][] pixels, Character infinity) {
        this.pixels = pixels;
        this.infinity = infinity;
    }

    public Character[][] getPixels() {
        return pixels;
    }

    public int getWidth() { return this.pixels[0].length; }
    public int getHeight() { return this.pixels.length; }

    public Character getPixel(int x, int y) {
        if (x < 0 || x >= getWidth()) {
            return infinity;
        }
        if (y < 0 || y >= getHeight()) {
            return infinity;
        }
        return pixels[y][x];
    }

    public Character[][] getNeighbourhood(int x, int y) {
        var neighbourhood = new Character[3][3];
        for (var i = 0; i < 3; i++) {
            for (var j = 0; j < 3; j++) {
                neighbourhood[j][i] = getPixel(x - 1 + i, y - 1 + j);
            }
        }
        return neighbourhood;
    }

    public Integer getNeighbourhoodIndex(int x, int y) {
        var neighbourhood = getNeighbourhood(x, y);
        var sb = new StringBuilder();
        for (var j = 0; j < 3; j++) {
            for (var i = 0; i < 3; i++) {
                sb.append(neighbourhood[j][i] == '#' ? '1' : '0');
            }
        }
        var binary = sb.toString();
        var result = Integer.parseInt(binary, 2);
        return result;
    }

    public int countPixels() {
        var count = 0;
        for (var i = 0; i < getWidth(); i++) {
            for (var j = 0; j < getHeight(); j++) {
                if (getPixel(i, j) == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var j = -2; j < getHeight() + 2; j++) {
            for (var i = -2; i < getWidth() + 2; i++) {
                sb.append(getPixel(i, j));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public Character getInfinity() {
        return infinity;
    }
}
