package sandkev.knight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 23/06/2016.
 */
public class Knight {

    public static List<Move> generateMoves(Board board){
        Position start = board.getPosition();
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
            if(board.validateMove(start, move)){
                moves.add(move);
            }
        }
        return moves;
    }

}
