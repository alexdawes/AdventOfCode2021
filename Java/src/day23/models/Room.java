package day23.models;

public class Room {
    private Amphipod[] container;
    private AmphipodType type;
    private int index;

    public Room(int size, AmphipodType type, int index) {
        this(new Amphipod[size], type, index);
    }

    private Room(Amphipod[] container, AmphipodType type, int index) {
        this.container = container;
        this.type = type;
        this.index = index;
    }

    public Amphipod get(int i) {
        return container[i];
    }

    public void put(int i, Amphipod amphipod) {
        container[i] = amphipod;
    }

    public int getIndex() {
        return index;
    }

    public int push(Amphipod amphipod) throws Exception {
        var energyCost = amphipod.getEnergyCost();
        var type = amphipod.getType();

        for (var i = container.length - 1; i >= 0; i--) {
            if (container[i] == null) {
                container[i] = amphipod;
                return energyCost * (i+1);
            }

            if (container[i].getType() != type) {
                break;
            }
        }

        throw new Exception("Cannot push " + amphipod + " into " + this);
    }

    public int pop(Out<Amphipod> amphipod) throws Exception {
        for (var i = 0; i < container.length; i++) {
            if (container[i] != null) {
                var amphipod1 = container[i];

                var done = true;
                for (var j = i; j < container.length; j++) {
                    if (container[j] != null && container[j].getType() != type) {
                        done = false;
                        break;
                    }
                }
                if (done) {
                    break;
                }
                var energyCost = amphipod1.getEnergyCost();
                amphipod.value = amphipod1;
                container[i] = null;
                return energyCost * (i+1);
            }
        }

        throw new Exception("Cannot pop from " + this);
    }

    public int size() {
        return container.length;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var i = 0; i < container.length; i++) {
            sb.append(container[i] != null ? container[i].toString() : ".");
        }
        return sb.toString();
    }

    public AmphipodType getType() {
        return type;
    }

    public boolean isFinished() {
        for (var i = 0; i < container.length; i++) {
            if (container[i] == null || container[i].getType() != type) {
                return false;
            }
        }
        return true;
    }

    public Room clone() {
        var newContainer = new Amphipod[container.length];
        for (var i = 0; i < newContainer.length; i++) {
            newContainer[i] = container[i] != null ? container[i].clone() : null;
        }
        return new Room(newContainer, type, index);
    }
}
