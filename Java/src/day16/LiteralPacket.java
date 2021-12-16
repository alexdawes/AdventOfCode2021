package day16;

public class LiteralPacket extends Packet{
    private long value;

    public LiteralPacket(int version, int typeId, long value) {
        super(version, typeId);
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    public static LiteralPacket Read(int version, int typeId, Reader reader) {
        var bits = new StringBuilder();

        while (true) {
            var next = reader.read(5);
            var prefix = next.charAt(0);
            var bin = next.substring(1);
            bits.append(bin);

            if (prefix == '0') {
                break;
            }
        }

        var value = Long.parseLong(bits.toString(), 2);

        return new LiteralPacket(version, typeId, value);
    }
}
