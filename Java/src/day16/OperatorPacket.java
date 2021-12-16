package day16;

import java.util.ArrayList;
import java.util.List;

public class OperatorPacket extends Packet {
    private int lengthTypeId;
    private List<Packet> subPackets;

    public OperatorPacket(int version, int typeId, int lengthTypeId, List<Packet> subPackets) {
        super(version, typeId);
        this.lengthTypeId = lengthTypeId;
        this.subPackets = subPackets;
    }

    @Override
    public int getTotalVersionNumbers() {
        return this.getVersion() + subPackets.stream().map(sp -> sp.getTotalVersionNumbers()).reduce(0, (a, b) -> a + b);
    }

    public int getLengthTypeId() {
        return lengthTypeId;
    }

    public List<Packet> getSubPackets() {
        return subPackets;
    }

    @Override
    public long getValue() {
        switch (getTypeId()) {
            case 0: {
                return subPackets.stream().map(sp -> sp.getValue()).reduce(0L, (a, b) -> a + b);
            }
            case 1: {
                return subPackets.stream().map(sp -> sp.getValue()).reduce(1L, (a, b) -> a * b);
            }
            case 2: {
                return subPackets.stream().map(sp -> sp.getValue()).min(Long::compare).get();
            }
            case 3: {
                return subPackets.stream().map(sp -> sp.getValue()).max(Long::compare).get();
            }
            case 5: {
                return subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1 : 0;
            }
            case 6: {
                return subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1 : 0;
            }
            case 7: {
                return subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1 : 0;
            }
            default: return 0;
        }
    }

    public static OperatorPacket Read(int version, int typeId, Reader reader) {
        var lengthTypeId = Integer.parseInt(reader.read(1), 2);
        switch (lengthTypeId) {
            case 0: {
                var length = Integer.parseInt(reader.read(15), 2);
                var bits = reader.read(length);
                var subPackets = Packet.ParseBin(bits);
                return new OperatorPacket(version, typeId, lengthTypeId, subPackets);
            }
            case 1: {
                var number = Integer.parseInt(reader.read(11), 2);
                var subPackets = new ArrayList<Packet>();
                for (var i = 0; i < number; i++) {
                    subPackets.add(Packet.Read(reader));
                }
                return new OperatorPacket(version, typeId, lengthTypeId, subPackets);
            }
            default: return null;
        }
    }
}
