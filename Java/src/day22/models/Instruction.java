package day22.models;

import java.util.regex.Pattern;

public class Instruction {
    private InstructionType type;
    private Cuboid cuboid;

    public Instruction(InstructionType type, Cuboid cuboid) {
        this.type = type;
        this.cuboid = cuboid;
    }

    public InstructionType getType() {
        return type;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }


    public static Instruction parse(String line) {
        var pattern = Pattern.compile("^(?<type>(on|off)) x=(?<xMin>-?\\d+)\\.\\.(?<xMax>-?\\d+),y=(?<yMin>-?\\d+)\\.\\.(?<yMax>-?\\d+),z=(?<zMin>-?\\d+)\\.\\.(?<zMax>-?\\d+)$");
        var matcher = pattern.matcher(line.trim());
        matcher.matches();

        var type = matcher.group("type").equals("on") ? InstructionType.On : InstructionType.Off;

        var xMin = Long.parseLong(matcher.group("xMin"));
        var xMax = Long.parseLong(matcher.group("xMax"));
        var yMin = Long.parseLong(matcher.group("yMin"));
        var yMax = Long.parseLong(matcher.group("yMax"));
        var zMin = Long.parseLong(matcher.group("zMin"));
        var zMax = Long.parseLong(matcher.group("zMax"));

        var xRange = new Range(xMin, xMax);
        var yRange = new Range(yMin, yMax);
        var zRange = new Range(zMin, zMax);

        var cuboid = new Cuboid(xRange, yRange, zRange);
        return new Instruction(type, cuboid);
    }

    @Override
    public String toString() {
        return (type == InstructionType.On ? "on" : "off") + " " + cuboid;
    }
}
