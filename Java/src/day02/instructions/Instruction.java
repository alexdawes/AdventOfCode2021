package day02.instructions;

import day02.positions.AdvancedPosition;
import day02.positions.BasicPosition;

public abstract class Instruction {
    public abstract BasicPosition Iterate(BasicPosition position);
    public abstract AdvancedPosition Iterate(AdvancedPosition position);

    public static Instruction Parse(String s) throws Exception {
        var split = s.split(" ");
        var inst = split[0];
        var value = Integer.parseInt(split[1]);
        switch (inst) {
            case "forward": {
                return new ForwardInstruction(value);
            }
            case "up": {
                return new UpInstruction(value);
            }
            case "down": {
                return new DownInstruction(value);
            }
            default:
                throw new Exception("Unrecognised inst: " + inst);
        }
    }
}
