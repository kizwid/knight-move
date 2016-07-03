package sandkev.knight;


import java.util.*;

/**
 * the board knows the keys that are available to be pressed
 */
public class Board {
    char[] values = new char[]{
            'A','B','C','D','E',
            'F','G','H','I','J',
            'K','L','M','N','O',
            ' ','1','2','3',' '
    };
    private Cell[][] cells;
    //private Position position;
    public static final int ROWS = 4;
    public static final int COLUMNS = 5;
    private Map<Character, char[]> movesByValue;
    private Map<Character, Cell> cellByValue;

    public Set<Cell> getActiveCells() {
        return activeCells;
    }

    private Set<Cell> activeCells;

    public Board() {
        this.activeCells = new LinkedHashSet<>();
        this.cellByValue = new HashMap<>();
        this.movesByValue = new HashMap<>();
        this.cells = new Cell[ROWS][COLUMNS];
        for(int row = 0;row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                cells[row][column] = new Cell(values[(row * COLUMNS) + column]);
            }
        }
        for(int row = 0;row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                Cell cell = cells[row][column];
                Position current = new Position(row, column);
                List<Move> moves = possibleMoves(current);
                char[] chars = new char[moves.size()];
                for(int n = 0; n < chars.length; n++){
                    chars[n] = at(current.applyMove(moves.get(n))).getValue();
                }
                if(cell.type!= Cell.Type.Empty){
                    activeCells.add(cell);
                    movesByValue.put(cell.getValue(), chars);
                    cellByValue.put(cell.getValue(), cell);
                }
            }
        }
    }

    public Cell at(Position position){
        return cells[position.getRow()][position.getColumn()];
    }

    public boolean validateMove(Position from, Move move) {
        Position to = from.applyMove(move);
        if(to.getColumn() < 0 || to.getRow() < 0){
            return false;
        }else if(to.getColumn() > COLUMNS-1 || to.getRow() > ROWS-1){
            return false;
        }
        return at(to).getType()!= Cell.Type.Empty;
    }

    private List<Move> possibleMoves(Position from) {
        Cell cell = at(from);
        if(cell.getType()== Cell.Type.Empty){
            return Collections.emptyList();
        }else {
            List<Move> moves = new ArrayList<>((int)Math.pow(4,2));
                int[][] offsets = new int[][]{
                        {+2,+1},
                        {-2,+1},
                        {-2,-1},
                        {+2,-1},
                        {+1,+2},
                        {-1,+2},
                        {-1,-2},
                        {+1,-2}

                };
                for (int[] offset : offsets) {
                    Move move = new Move(offset[0], offset[1]);
                    if(validateMove(from, move)){
                        moves.add(move);
                    }
                }
            return moves;
        }
    }

    public char[] charsFor(char value) {
        return movesByValue.get(value);
    }

    public Cell cellFor(Character c) {
        return cellByValue.get(c);
    }
}

