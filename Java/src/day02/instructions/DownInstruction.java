package day02.instructions;

import day02.positions.AdvancedPosition;
import day02.positions.BasicPosition;

public class DownInstruction extends Instruction {
    private Integer value;

    public DownInstruction(Integer value) {
        this.value = value;
    }

    @Override
    public BasicPosition Iterate(BasicPosition position) {
        return new BasicPosition(
                position.getHorizontal(),
                position.getDepth() + value);
    }

    @Override
    public AdvancedPosition Iterate(AdvancedPosition position) {
        return new AdvancedPosition(
                position.getHorizontal(),
                position.getDepth(),
                position.getAim() + value);
    }
}
