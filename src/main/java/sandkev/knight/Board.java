package sandkev.knight;


import java.util.*;

/**
 * Created by kevin on 23/06/2016.
 */
public class Board {
    char[] values = new char[]{
            'A','B','C','D','E',
            'F','G','H','I','J',
            'K','L','M','N','O',
            ' ','1','2','3',' '
    };
    private Cell[][] cells;
    private Position position;
    public static final int ROWS = 4;
    public static final int COLUMNS = 5;
    private Map<Cell, List<Move>> movesByCell;

    public Board(Position initialPosition) {
        this.cells = new Cell[ROWS][COLUMNS];
        for(int row = 0;row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                cells[row][column] = new Cell(values[(row * COLUMNS) + column]);
            }
        }
        this.position = new Position(0, 0);
        this.movesByCell = new HashMap<>();
    }

    public Position getPosition(){
        return Position.of(position);
    }
    public Cell at(Position position){
        return cells[position.getRow()][position.getColumn()];
    }

    public synchronized Cell move(Move move){
        Position to = position.applyMove(move);
        this.position = to;
        return at(position);
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

    public List<Move> possibleMoves(Position from) {
        Cell cell = at(from);
        if(cell.getType()== Cell.Type.Empty){
            return Collections.emptyList();
        }else {
            List<Move> moves = movesByCell.get(cell);
            if(moves==null){
                movesByCell.put(cell, moves = new ArrayList<>((int)Math.pow(4,2)));
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
            }
            return moves;
        }
    }
}

