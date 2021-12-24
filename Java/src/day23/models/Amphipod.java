package day23.models;

import java.util.HashMap;
import java.util.Map;

public class Amphipod {
    private AmphipodType type;

    public Amphipod(AmphipodType type) {
        this.type = type;
    }

    public AmphipodType getType() {
        return type;
    }

    public int getEnergyCost() {
        return ENERGY_COSTS.get(type);
    }

    @Override
    public String toString() {
        switch (type) {
            case Amber: return "A";
            case Bronze: return "B";
            case Copper: return "C";
            case Desert: return "D";
            default: return "X";
        }
    }

    public static Map<AmphipodType, Integer> ENERGY_COSTS = new HashMap<>();
    static {
        ENERGY_COSTS.put(AmphipodType.Amber, 1);
        ENERGY_COSTS.put(AmphipodType.Bronze, 10);
        ENERGY_COSTS.put(AmphipodType.Copper, 100);
        ENERGY_COSTS.put(AmphipodType.Desert, 1000);
    }

    public static int getEnergyCost(AmphipodType type) {
        return ENERGY_COSTS.get(type);
    }

    public Amphipod clone() {
        return new Amphipod(type);
    }

    public static Amphipod parse(char c) {
        switch (c) {
            case 'A': return new Amphipod(AmphipodType.Amber);
            case 'B': return new Amphipod(AmphipodType.Bronze);
            case 'C': return new Amphipod(AmphipodType.Copper);
            case 'D': return new Amphipod(AmphipodType.Desert);
            default: return null;
        }
    }
}
