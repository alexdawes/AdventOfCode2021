package day17;

import java.util.regex.Pattern;

public class Trench {
    private Target target;
    private Coordinate origin;

    public Trench(Target target, Coordinate origin) {
        this.target = target;
        this.origin = origin;
    }

    public Target getTarget() {
        return target;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public boolean TestX(int velocity) {
        var vX = velocity;
        var pX = origin.getX();

        while (pX <= target.getXRange().getMax()) {
            pX += vX;
            if (vX > 0) {
                vX--;
            } else if (vX < 0) {
                vX++;
            }

            if (target.getXRange().contains(pX)) {
                return true;
            }

            if (vX == 0) {
                return false;
            }
        }
        return false;
    }

    public boolean TestY(int velocity) {
        var vY = velocity;
        var pY = origin.getY();

        while (pY >= target.getYRange().getMin()) {
            pY += vY;
            vY--;

            if (target.getYRange().contains(pY)) {
                return true;
            }
        }
        return false;
    }

    public boolean Test(int xVelocity, int yVelocity) {
        var position = origin;

        var vX = xVelocity;
        var vY = yVelocity;

        while (position.getX() <= target.getXRange().getMax() && position.getY() >= target.getYRange().getMin()) {
            position = new Coordinate(position.getX() + vX, position.getY() + vY);

            if (target.contains(position)) {
                return true;
            }

            if (vX > 0) {
                vX--;
            }
            else if (vX < 0) {
                vX++;
            }

            vY--;
        }

        return false;
    }

    public static Trench parse(String line) throws Exception {
        var pattern = Pattern.compile("target area: x=(?<xMin>-?\\d+)\\.\\.(?<xMax>-?\\d+), y=(?<yMin>-?\\d+)\\.\\.(?<yMax>-?\\d+)");
        var matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new Exception(line + " is not correct.");
        }
        var xMin = Integer.parseInt(matcher.group("xMin"));
        var xMax = Integer.parseInt(matcher.group("xMax"));
        var yMin = Integer.parseInt(matcher.group("yMin"));
        var yMax = Integer.parseInt(matcher.group("yMax"));
        var xRange = new Range(xMin, xMax);
        var yRange = new Range(yMin, yMax);
        var target = new Target(xRange, yRange);
        var origin = new Coordinate(0, 0);
        return new Trench(target, origin);
    }
}
