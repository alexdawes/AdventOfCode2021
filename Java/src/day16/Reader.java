package day16;

public class Reader {
    private String value;
    private Integer pointer;

    public Reader(String value) {
        this.value = value;
        this.pointer = 0;
    }

    public String getValue() { return value; }
    public Integer getPointer() { return pointer; }

    public String read(Integer length) {
        var next = peek(length);
        pointer += length;
        return next;
    }

    public String peek(Integer length) {
        return pointer + length >= value.length() ? this.value.substring(pointer) : this.value.substring(pointer, pointer + length);
    }

    public boolean isFinished() {
        return pointer >= value.length();
    }
}
