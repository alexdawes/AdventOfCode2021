package day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Packet {
    private int version;
    private int typeId;

    public Packet (int version, int typeId) {

        this.version = version;
        this.typeId = typeId;
    }

    public int getVersion() {
        return version;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getTotalVersionNumbers() {
        return version;
    }

    public long getValue() {
        return 0L;
    }

    public static Packet Read(Reader reader) {
        var peek = reader.peek(8);
        if (peek.length() != 8) {
            return null;
        }

        var verBits = reader.read(3);
        var version = Integer.parseInt(verBits, 2);

        var typeIdBits = reader.read(3);
        var typeId = Integer.parseInt(typeIdBits, 2);

        switch (typeId) {
            case 4: {
                return LiteralPacket.Read(version, typeId, reader);
            }
            default: {
                return OperatorPacket.Read(version, typeId, reader);
            }
        }
    }

    public static List<Packet> ParseHex(String hex) {
        var bin = ConvertToBinary(hex);
        return ParseBin(bin);
    }

    public static List<Packet> ParseBin(String bin) {
        var reader = new Reader(bin);

        var result = new ArrayList<Packet>();
        while (!reader.isFinished()) {
            var next = Read(reader);
            if (next != null) {
                result.add(next);
            } else {
                break;
            }
        }
        return result;
    }

    private static String ConvertToBinary(String hex) {
        var hexMap = new HashMap<Character, String>();
        hexMap.put('0', "0000");
        hexMap.put('1', "0001");
        hexMap.put('2', "0010");
        hexMap.put('3', "0011");
        hexMap.put('4', "0100");
        hexMap.put('5', "0101");
        hexMap.put('6', "0110");
        hexMap.put('7', "0111");
        hexMap.put('8', "1000");
        hexMap.put('9', "1001");
        hexMap.put('A', "1010");
        hexMap.put('B', "1011");
        hexMap.put('C', "1100");
        hexMap.put('D', "1101");
        hexMap.put('E', "1110");
        hexMap.put('F', "1111");

        var builder = new StringBuilder();
        for (var c : hex.toCharArray()) {
            var mapped = hexMap.get(c);
            builder.append(mapped);
        }

        return builder.toString();
    }


}
