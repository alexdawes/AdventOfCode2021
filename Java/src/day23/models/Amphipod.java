package day23.models;

import java.util.*;

public class Amphipod {
    private final AmphipodType type;
    private BurrowPosition position;
    private Burrow burrow;

    public static Map<AmphipodType, Integer> ENERGY_COSTS = new HashMap<>();

    static {
        ENERGY_COSTS.put(AmphipodType.Amber, 1);
        ENERGY_COSTS.put(AmphipodType.Bronze, 10);
        ENERGY_COSTS.put(AmphipodType.Copper, 100);
        ENERGY_COSTS.put(AmphipodType.Desert, 1000);
    }

    public Amphipod(AmphipodType type, BurrowPosition position) {
        this.type = type;
        this.position = position;
    }

    public void setBurrow(Burrow burrow) {
        this.burrow = burrow;
    }

    public AmphipodType getType() {
        return type;
    }

    public BurrowPosition getPosition() {
        return position;
    }

    public BurrowRoom getDesiredRoom() {
        switch (type) {
            case Amber: return BurrowRoom.Side1;
            case Bronze: return BurrowRoom.Side2;
            case Copper: return BurrowRoom.Side3;
            case Desert: return BurrowRoom.Side4;
            default: return null;
        }
    }

    public int move(BurrowPosition position) {
        var moveCost = ENERGY_COSTS.get(type);
        var distance = BurrowPosition.getDistance(this.position, position);
        this.position = position;
        return moveCost * distance;
    }

    @Override
    public String toString() {
        switch (type) {
            case Amber: return "A@" + position;
            case Bronze: return "B@" + position;
            case Copper: return "C@" + position;
            case Desert: return "D@" + position;
            default: return ".";
        }
    }

    public String toShortString() {
        switch (type) {
            case Amber: return "A";
            case Bronze: return "B";
            case Copper: return "C";
            case Desert: return "D";
            default: return "X";
        }
    }

    public Amphipod clone() {
        return new Amphipod(type, position);
    }

    public boolean isFinished() {
        var desired = getDesiredRoom();
        var room = position.getRoom();
        var pos = position.getPosition();
        return room == desired && pos == 1 || (pos == 0 && burrow.isOccupied(room, 1) && burrow.getAmphipodAt(room, 1).type == type);
    }

    public boolean isFree() {
        var room = position.getRoom();
        var pos = position.getPosition();
        return room == BurrowRoom.Main || pos == 0 || pos == 1 && !burrow.isOccupied(room, 0);
    }

    public List<BurrowPosition> getAvailableMoves() {
        if (!isFree()) {
            return new ArrayList<>();
        }
        var allMoves = getAllMoves();

        var result = new ArrayList<BurrowPosition>();
        for (var move : allMoves) {
            var path = BurrowPosition.getPath(position, move);

            if (path.stream().allMatch(pos -> !burrow.isOccupied(pos))) {
                result.add(move);
            }
        }
        return result;
    }

    public List<BurrowPosition> getAllMoves() {
        if (isFinished()) {
            return new ArrayList<>();
        }

        var room = position.getRoom();

        if (room == BurrowRoom.Main) {
            var desired = getDesiredRoom();
            var desiredPos = burrow.isOccupied(desired, 1) && burrow.getAmphipodAt(desired, 1).type == type ? 0 : 1;
            return Arrays.asList(new BurrowPosition(desired, desiredPos));
        }

        return Arrays.asList(
                new BurrowPosition(BurrowRoom.Main, 0),
                new BurrowPosition(BurrowRoom.Main, 1),
                new BurrowPosition(BurrowRoom.Main, 3),
                new BurrowPosition(BurrowRoom.Main, 5),
                new BurrowPosition(BurrowRoom.Main, 7),
                new BurrowPosition(BurrowRoom.Main, 9),
                new BurrowPosition(BurrowRoom.Main, 10)
        );
    }

    public static Amphipod parse(Character c, BurrowPosition position) {
        switch (c) {
            case 'A': return new Amphipod(AmphipodType.Amber, position);
            case 'B': return new Amphipod(AmphipodType.Bronze, position);
            case 'C': return new Amphipod(AmphipodType.Copper, position);
            case 'D': return new Amphipod(AmphipodType.Desert, position);
            default: return null;
        }
    }
}
