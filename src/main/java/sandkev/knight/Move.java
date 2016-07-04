package sandkev.knight;

/**
 * Created by kevin on 4/07/2016.
 */
public class Move {
    private final int rowOffset;
    private final int columnOffset;
    public Move(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }
    public int getRowOffset() {
        return rowOffset;
    }
    public int getColumnOffset() {
        return columnOffset;
    }
}
