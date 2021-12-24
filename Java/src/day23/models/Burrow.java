package day23.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Burrow {
    private Room[] rooms;
    private Hall hall;
    private int energy;

    private Integer heuristic;

    public Burrow(Room[] rooms) {
        this(new Hall(), rooms, 0);
    }

    private Burrow(Hall hall, Room[] rooms, int energy) {
        this.rooms = rooms;
        this.hall = hall;
        this.energy = energy;
    }

    public boolean isComplete() {
        for (var room : rooms) {
            if (!room.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public int getEnergy() {
        return energy;
    }

    public Burrow clone() {
        var newRooms = new Room[rooms.length];
        for (var i = 0; i < rooms.length; i++) {
            newRooms[i] = rooms[i].clone();;
        }

        return new Burrow(hall.clone(), newRooms, energy);
    }

    public int getHeuristic() {
        if (heuristic == null) {
            heuristic = (getEstimate() + getEnergy());
        }
        return heuristic;
    }

    public int getEstimate() {
        var total = 0;

        for (var i = 0; i < hall.size(); i++) {
            var amph = hall.get(i);
            if (amph != null) {
                var type = amph.getType();
                var targetRoom = getRoom(type);
                var index = targetRoom.getIndex();
                var dist = Math.abs(i - index);
                var energyCost = amph.getEnergyCost();
                total += energyCost * (dist + targetRoom.size());
            }
        }

        for (var room : rooms) {
            for (var i = 0; i < room.size(); i++) {
                var done = true;
                for (var j = i; j < room.size(); j++) {
                    if (room.get(j) != null && room.get(j).getType() != room.getType()) {
                        done = false;
                        break;
                    }
                }

                if (!done) {
                    var amph = room.get(i);
                    if (amph != null) {
                        var energyCost = amph.getEnergyCost();
                        var index = room.getIndex();
                        var type = amph.getType();
                        var targetRoom = getRoom(type);
                        var dist = Math.abs(index - targetRoom.getIndex());
                        total += energyCost * (room.size() + targetRoom.size() + dist);
                    }
                }
            }
        }

        return total;
    }

    public Room getRoom(AmphipodType type) {
        for (var room : rooms) {
            if (room.getType() == type) {
                return room;
            }
        }
        return null;
    }

    public static Burrow parse(List<String> lines) {
        var roomSize = lines.size() - 3;

        var aRoom = new Room(roomSize, AmphipodType.Amber, 2);
        var bRoom = new Room(roomSize, AmphipodType.Bronze, 4);
        var cRoom = new Room(roomSize, AmphipodType.Copper, 6);
        var dRoom = new Room(roomSize, AmphipodType.Desert, 8);

        for (var i = 0; i < roomSize; i++) {
            var a = Amphipod.parse(lines.get(i + 2).charAt(3));
            var b = Amphipod.parse(lines.get(i + 2).charAt(5));
            var c = Amphipod.parse(lines.get(i + 2).charAt(7));
            var d = Amphipod.parse(lines.get(i + 2).charAt(9));
            aRoom.put(i, a);
            bRoom.put(i, b);
            cRoom.put(i, c);
            dRoom.put(i, d);
        }
        Room[] rooms = { aRoom, bRoom, cRoom, dRoom };
        return new Burrow(rooms);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("#############\n");

        sb.append("#");
        for (var i = 0; i < hall.size(); i++) {
            sb.append(hall.get(i) != null ? hall.get(i).toString() : ".");
        }
        sb.append("#\n");

        var roomSize = rooms[0].size();
        for (var i = 0; i < roomSize; i++) {
            sb.append(i == 0 ? "###" : "  #");
            for (var j = 0; j < rooms.length; j++) {
                var room = rooms[j];
                var amph = room.get(i);
                sb.append(amph != null ? amph.toString() : ".");
                if (j != rooms.length - 1) {
                    sb.append("#");
                }
            }
            sb.append(i == 0 ? "###\n" : "#\n");
        }

        sb.append("  #########\n");
        return sb.toString();
    }

    public void addEnergy(int value) {
        energy += value;
    }

    public List<Burrow> getNext() {
        var result = new ArrayList<Burrow>();
        for (var room : rooms) {
            try {
                var clone = this.clone();
                var cloneRoom = clone.getRoom(room.getType());
                var index = cloneRoom.getIndex();
                var out = new Out<Amphipod>(null);
                var energy = cloneRoom.pop(out);
                var amph = out.value;
                var type = amph.getType();

                int[] indices = {0,1,3,5,7,9,10};
                for (var i : indices) {
                    try {
                        var clone2 = clone.clone();
                        if (index < i) {
                            for (var j = index + 1; j <= i; j++) {
                                if (clone2.hall.get(j) != null) {
                                    throw new Exception("Cannot walk");
                                }
                            }
                        }
                        else {
                            for (var j = index - 1; j >= i; j--) {
                                if (clone2.hall.get(j) != null) {
                                    throw new Exception("Cannot walk");
                                }
                            }
                        }

                        var dist = Math.abs(index - i);
                        var energyCost = amph.getEnergyCost();

                        var energyTotal = energy + (dist * energyCost);
                        clone2.addEnergy(energyTotal);
                        clone2.hall.put(i, amph);
                        result.add(clone2);
                    }
                    catch (Exception e) {
                    }
                }
            }
            catch (Exception e) {
            }
        }

        for (var i = 0; i < hall.size(); i++) {
            var amph = hall.get(i);
            if (amph != null) {
                try {
                    var clone = this.clone();
                    var type = amph.getType();
                    var targetRoom = clone.getRoom(type);
                    var index = targetRoom.getIndex();

                    if (i < index) {
                        for (var j = i + 1; j <= index; j++) {
                            if (clone.hall.get(j) != null) {
                                throw new Exception("Cannot walk");
                            }
                        }
                    }
                    else {
                        for (var j = i - 1; j >= index; j--) {
                            if (clone.hall.get(j) != null) {
                                throw new Exception("Cannot walk");
                            }
                        }
                    }

                    var dist = Math.abs(i - index);
                    var energyCost = amph.getEnergyCost();
                    var energyTotal = (dist * energyCost) + targetRoom.push(amph);
                    clone.hall.remove(i);
                    clone.addEnergy(energyTotal);
                    result.add(clone);
                }
                catch(Exception e) {

                }
            }
        }

        return result;
    }
}
