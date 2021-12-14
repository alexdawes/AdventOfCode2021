package day02.instructions;

import day02.positions.AdvancedPosition;
import day02.positions.BasicPosition;

public class ForwardInstruction extends Instruction {
    private Integer value;

    public ForwardInstruction(Integer value) {

        this.value = value;
    }

    @Override
    public BasicPosition Iterate(BasicPosition position) {
        return new BasicPosition(position.getHorizontal() + value, position.getDepth());
    }

    @Override
    public AdvancedPosition Iterate(AdvancedPosition position) {
        return new AdvancedPosition(
                position.getHorizontal() + value,
                position.getDepth() + (position.getAim() * value),
                position.getAim());
    }
}