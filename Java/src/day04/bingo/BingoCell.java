package day04.bingo;

public class BingoCell {
    private int value;
    private boolean checked;

    public BingoCell(int value, boolean checked) {
        this.value = value;
        this.checked = checked;
    }

    public int getValue() { return value; }

    public boolean getChecked() { return checked; }
    public void setChecked(boolean value) { checked = value; }

    public void accept(int value) {
        if (this.value == value) {
            checked = true;
        }
    }
}
