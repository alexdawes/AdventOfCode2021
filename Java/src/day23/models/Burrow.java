package day23.models;

import java.util.ArrayList;
import java.util.List;

public class Burrow {

    private List<Amphipod> amphipods;

    public Burrow(List<Amphipod> amphipods) {
        for (var amph : amphipods) {
            amph.setBurrow(this);
        }
        this.amphipods = amphipods;
    }

    public Burrow clone() {
        var newAmphs = new ArrayList<Amphipod>();
        for (var amph : amphipods) {
            newAmphs.add(amph.clone());
        }
        return new Burrow(newAmphs);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("#############\n");

        sb.append("#");
        for (var i = 0; i < 11; i++) {
            var a = getAmphipodAt(BurrowRoom.Main, i);
            sb.append(a == null ? "." : a.toShortString());
        }
        sb.append("#\n");

        sb.append("###");
        var a1 = getAmphipodAt(BurrowRoom.Side1, 0);
        sb.append(a1 == null ? "." : a1.toShortString());
        sb.append("#");
        var b1 = getAmphipodAt(BurrowRoom.Side2, 0);
        sb.append(b1 == null ? "." : b1.toShortString());
        sb.append("#");
        var c1 = getAmphipodAt(BurrowRoom.Side3, 0);
        sb.append(c1 == null ? "." : c1.toShortString());
        sb.append("#");
        var d1 = getAmphipodAt(BurrowRoom.Side4, 0);
        sb.append(d1 == null ? "." : d1.toShortString());
        sb.append("###\n");


        sb.append("  #");
        var a2 = getAmphipodAt(BurrowRoom.Side1, 1);
        sb.append(a2 == null ? "." : a2.toShortString());
        sb.append("#");
        var b2 = getAmphipodAt(BurrowRoom.Side2, 1);
        sb.append(b2 == null ? "." : b2.toShortString());
        sb.append("#");
        var c2 = getAmphipodAt(BurrowRoom.Side3, 1);
        sb.append(c2 == null ? "." : c2.toShortString());
        sb.append("#");
        var d2 = getAmphipodAt(BurrowRoom.Side4, 1);
        sb.append(d2 == null ? "." : d2.toShortString());
        sb.append("#  \n");

        sb.append("  #########  \n");

        return sb.toString();
    }

    public Long solveLeastEnergy() {
        if (amphipods.stream().allMatch(a -> a.isFinished())) {
            return 0L;
        }

        var moves = new ArrayList<AmphipodMove>();
        for (var amph : amphipods) {
            var amphMoves = amph.getAvailableMoves();
            for (var amphMove : amphMoves) {
                var move = new AmphipodMove(amph, amphMove);
                moves.add(move);
            }
        }

        if (moves.size() == 0) {
            return null;
        }

        Long least = null;
        for (var move : moves) {
            var newBurrow = clone();
            var amph = newBurrow.getAmphipodAt(move.getAmphipod().getPosition());
            var cost = amph.move(move.getPosition());
            var remaining = newBurrow.solveLeastEnergy();
            if (remaining == null) {
                continue;
            }
            var total = cost + remaining;
            if (least == null || total < least) {
                least = total;
            }
        }
        return least;
    }

    public Amphipod getAmphipodAt(BurrowPosition position) {
        for (var amph : amphipods) {
            if (amph.getPosition().equals(position)) {
                return amph;
            }
        }
        return null;
    }

    public Amphipod getAmphipodAt(BurrowRoom room, int position) {
        return getAmphipodAt(new BurrowPosition(room, position));
    }

    public boolean isOccupied(BurrowPosition position) {
        return getAmphipodAt(position) != null;
    }

    public boolean isOccupied(BurrowRoom room, int position) {
        return isOccupied(new BurrowPosition(room, position));
    }

    public static Burrow parse(List<String> lines) {
        var a1 = Amphipod.parse(lines.get(2).charAt(3), new BurrowPosition(BurrowRoom.Side1, 0));
        var a2 = Amphipod.parse(lines.get(3).charAt(3), new BurrowPosition(BurrowRoom.Side1, 1));
        var b1 = Amphipod.parse(lines.get(2).charAt(5), new BurrowPosition(BurrowRoom.Side2, 0));
        var b2 = Amphipod.parse(lines.get(3).charAt(5), new BurrowPosition(BurrowRoom.Side2, 1));
        var c1 = Amphipod.parse(lines.get(2).charAt(7), new BurrowPosition(BurrowRoom.Side3, 0));
        var c2 = Amphipod.parse(lines.get(3).charAt(7), new BurrowPosition(BurrowRoom.Side3, 1));
        var d1 = Amphipod.parse(lines.get(2).charAt(9), new BurrowPosition(BurrowRoom.Side4, 0));
        var d2 = Amphipod.parse(lines.get(3).charAt(9), new BurrowPosition(BurrowRoom.Side4, 1));

        var lst = new ArrayList<Amphipod>();
        lst.add(a1);
        lst.add(a2);
        lst.add(b1);
        lst.add(b2);
        lst.add(c1);
        lst.add(c2);
        lst.add(d1);
        lst.add(d2);
        return new Burrow(lst);
    }
}
