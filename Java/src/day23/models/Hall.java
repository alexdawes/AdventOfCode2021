package day23.models;

public class Hall {
    private Amphipod[] container;
    public Hall() {
        this(new Amphipod[11]);
    }

    private Hall(Amphipod[] container) {
        this.container = container;
    }

    public int size() { return container.length; }

    public Amphipod get(int i) { return container[i]; }
    public void put(int i, Amphipod amphipod) {
        container[i] = amphipod;
    }
    public void remove(int i) { container[i] = null; }

    public Hall clone() {
        var newContainer = new Amphipod[container.length];
        for (var i = 0; i < container.length; i++) {
            newContainer[i] = container[i] != null ? container[i].clone() : null;
        }
        return new Hall(newContainer);
    }
}
