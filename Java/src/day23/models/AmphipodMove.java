package day23.models;

import java.util.List;

public class AmphipodMove {
    private Amphipod amphipod;
    private BurrowPosition position;

    public AmphipodMove(Amphipod amphipod, BurrowPosition position) {

        this.amphipod = amphipod;
        this.position = position;
    }

    public Amphipod getAmphipod() {
        return amphipod;
    }

    public BurrowPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return amphipod + "=>" + position;
    }
}
