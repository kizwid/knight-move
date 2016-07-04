package sandkev.knight;

/**
 * Created by kevin on 4/07/2016.
 */
public class Position {
    private final int row;
    private final int column;
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Position applyMove(Move move) {
        return new Position(row + move.getRowOffset(), column + move.getColumnOffset());
    }

    public static Position of(Position position){
        return new Position(position.getRow(), position.getColumn());
    }
}
