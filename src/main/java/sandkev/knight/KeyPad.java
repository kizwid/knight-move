package sandkev.knight;


import java.util.*;

/**
 * the keypad knows the keys that are available to be pressed
 */
public class KeyPad {
    char[] values = new char[]{
            'A','B','C','D','E',
            'F','G','H','I','J',
            'K','L','M','N','O',
            ' ','1','2','3',' '
    };
    private Key[][] keys;
    public static final int ROWS = 4;
    public static final int COLUMNS = 5;
    private final Map<Character, char[]> movesByValue;
    private final Map<Character, Key> keyByValue;
    private Set<Key> activeKeys;

    public KeyPad() {
        this.activeKeys = new LinkedHashSet<>();
        this.keyByValue = new HashMap<>();
        this.movesByValue = new HashMap<>();
        this.keys = new Key[ROWS][COLUMNS];
        for(int row = 0;row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                keys[row][column] = new Key(values[(row * COLUMNS) + column]);
            }
        }
        for(int row = 0;row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                Key key = keys[row][column];
                Position current = new Position(row, column);
                List<Move> moves = possibleMoves(current);
                char[] chars = new char[moves.size()];
                for(int n = 0; n < chars.length; n++){
                    chars[n] = at(current.applyMove(moves.get(n))).getValue();
                }
                if(key.getType()!= Key.Type.Empty){
                    activeKeys.add(key);
                    movesByValue.put(key.getValue(), chars);
                    keyByValue.put(key.getValue(), key);
                }
            }
        }
    }

    public Set<Key> getActiveKeys() {
        return activeKeys;
    }

    public Key at(Position position){
        return keys[position.getRow()][position.getColumn()];
    }

    private boolean validateMove(Position from, Move move) {
        Position to = from.applyMove(move);
        if(to.getColumn() < 0 || to.getRow() < 0){
            return false;
        }else if(to.getColumn() > COLUMNS-1 || to.getRow() > ROWS-1){
            return false;
        }
        return at(to).getType()!= Key.Type.Empty;
    }

    private List<Move> possibleMoves(Position from) {
        Key key = at(from);
        if(key.getType()== Key.Type.Empty){
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
}

