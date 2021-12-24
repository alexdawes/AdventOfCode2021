package day23.models;

import java.util.*;

public class BurrowPosition {
    private BurrowRoom room;
    private int position;

    public BurrowPosition(BurrowRoom room, int position) {
        this.room = room;
        this.position = position;
    }

    public BurrowRoom getRoom() {
        return room;
    }

    public int getPosition() {
        return position;
    }

    public static Map<BurrowRoom, Integer> ENTRANCE_POSITIONS = new HashMap<>();
    static {
        ENTRANCE_POSITIONS.put(BurrowRoom.Side1, 2);
        ENTRANCE_POSITIONS.put(BurrowRoom.Side2, 4);
        ENTRANCE_POSITIONS.put(BurrowRoom.Side3, 6);
        ENTRANCE_POSITIONS.put(BurrowRoom.Side4, 8);
    }

    public static List<BurrowPosition> getPath(BurrowPosition from, BurrowPosition to) {
        if (from.room == to.room) {
            if (from.position == to.position) {
                return new ArrayList<>();
            }
            else {
                return Arrays.asList(to);
            }
        }

        var start = new ArrayList<BurrowPosition>();
        if (from.room != BurrowRoom.Main) {
            if (from.position == 1) {
                start.add(new BurrowPosition(from.room, 0));
            }
            from = new BurrowPosition(BurrowRoom.Main, ENTRANCE_POSITIONS.get(from.room));
            start.add(from);
        }

        var end = new ArrayList<BurrowPosition>();
        if (to.room != BurrowRoom.Main) {
            if (to.position == 1) {
                end.add(new BurrowPosition(to.room, 0));
            }
            end.add(to);
            to = new BurrowPosition(BurrowRoom.Main, ENTRANCE_POSITIONS.get(to.room));
        }
        var middle = new ArrayList<BurrowPosition>();
        if (from.position < to.position) {
            for (var i = from.position + 1; i <= to.position; i++) {
                middle.add(new BurrowPosition(BurrowRoom.Main, i));
            }
        }
        if (from.position > to.position) {
            for (var i = from.position - 1; i >= to.position; i--) {
                middle.add(new BurrowPosition(BurrowRoom.Main, i));
            }
        }

        var result = new ArrayList<BurrowPosition>();
        result.addAll(start);
        result.addAll(middle);
        result.addAll(end);
        return result;
    }

    public static int getDistance(BurrowPosition from, BurrowPosition to) {
        if (from.room == to.room) {
            return Math.abs(from.position - to.position);
        }

        var total = 0;
        if (from.room != BurrowRoom.Main) {
            total += from.position + 1;
            from = new BurrowPosition(BurrowRoom.Main, ENTRANCE_POSITIONS.get(from.room));
        }
        if (to.room != BurrowRoom.Main) {
            total += to.position + 1;
            to = new BurrowPosition(BurrowRoom.Main, ENTRANCE_POSITIONS.get(to.room));
        }

        return total + Math.abs(to.position - from.position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BurrowPosition that = (BurrowPosition) o;
        return position == that.position && room == that.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, position);
    }

    @Override
    public String toString() {;
        switch (room) {
            case Main: return "M" + position;
            case Side1: return "A" + position;
            case Side2: return "B" + position;
            case Side3: return "C" + position;
            case Side4: return "D" + position;
            default: return "X" + position;
        }
    }
}
