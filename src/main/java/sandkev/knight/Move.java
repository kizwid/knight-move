package sandkev.knight;

/**
 * Created by kevin on 29/06/2016.
 */
public class Move {
    private int rowOffset;
    private int columnOffset;
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
