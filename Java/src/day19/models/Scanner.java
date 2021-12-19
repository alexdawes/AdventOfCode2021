package day19.models;

import java.util.*;
import java.util.regex.Pattern;

public class Scanner {
    private Integer id;
    private Vector position;
    private Set<Vector> beacons;

    public Scanner(Integer id, Vector position,
                   Set<Vector> beacons) {
        this.id = id;
        this.position = position;
        this.beacons = beacons;
    }

    public List<Integer> getDistances() throws Exception {
        var beaconList = beacons.stream().toList();
        var distList = new ArrayList<Integer>();

        for (var i = 0; i < beaconList.size(); i++) {
            for (var j = i+1; j < beaconList.size(); j++) {
                var iBeacon = beaconList.get(i);
                var jBeacon = beaconList.get(j);
                var distance = iBeacon.distanceTo(jBeacon);
                distList.add(distance);
            }
        }

        distList.sort(Integer::compare);
        return distList;
    }

    public Integer getId() { return id; }
    public Vector getPosition() { return position; }
    public Set<Vector> getBeacons() { return beacons; }

    public int countDistanceIntersects(Scanner other) throws Exception {
        var distList = getDistances();
        var otherDistList = other.getDistances();
        var i = 0;
        var j = 0;
        var count = 0;
        while (i < distList.size() && j < otherDistList.size()) {
            int iDist = distList.get(i);
            int jDist = otherDistList.get(j);

            if (iDist == jDist) {
                i++;
                j++;
                count++;
            }
            else if (iDist < jDist) {
                i++;
            }
            else if (jDist < iDist) {
                j++;
            }
        }
        return count;
    }

    public boolean testAgainst(Scanner other) throws Exception {
        var count = countDistanceIntersects(other);
        if (count >= (12 * 11 / 2)) {
            return true;
        }
        return false;
    }

    public static Scanner parse(String line) throws Exception {
        var lines = line.split("\\r\\n");
        var first = lines[0];
        var rest = Arrays.stream(lines).skip(1).toList();

        var idPattern = Pattern.compile("---\\sscanner\\s(?<id>\\d+)\\s---");
        var idMatch = idPattern.matcher(first.trim());
        idMatch.matches();
        var id = Integer.parseInt(idMatch.group("id"));

        var beacons = new HashSet<Vector>();
        for (var l : rest) {
            var splt = l.trim().split(",");
            var x = Integer.parseInt(splt[0]);
            var y = Integer.parseInt(splt[1]);
            var z = Integer.parseInt(splt[2]);
            beacons.add(new Vector(x, y, z));
        }

        return new Scanner(id, new Vector(new int[] { 0, 0, 0 }), beacons);
    }

    public static List<Scanner> parseAll(String line) throws Exception {
        var split = line.split("\\r\\n\\r\\n");

        var result = new ArrayList<Scanner>();
        for (var i = 0; i < split.length; i++) {
            result.add(parse(split[i]));
        }
        return result;
    }

    public void rotate(Matrix rotation) {
        beacons = new HashSet<>(beacons.stream().map(b -> b.transform(rotation)).toList());
        position = position.transform(rotation);
    }

    public void translate(Vector translation) {
        beacons = new HashSet<>(beacons.stream().map(b -> b.add(translation)).toList());
        position = position.add(translation);
    }

    public boolean fitTo(Scanner base) {
        for (var rot : Matrix3d.getAllRotations()) {
            var rotBeacons = beacons.stream().map(b -> b.transform(rot)).toList();
            for (var beacon : rotBeacons) {
                for (var baseBeacon : base.beacons) {
                    var diff = baseBeacon.subtract(beacon);
                    if (rotBeacons.stream().filter(b -> base.beacons.contains(b.add(diff))).count() >= 12) {
                        rotate(rot);
                        translate(diff);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Scanner " + id;
    }
}
