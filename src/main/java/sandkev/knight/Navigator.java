package sandkev.knight;

import java.util.*;

/**
 * the navigators job is to ensure that this path is unique
 * ie previously untrodden
 */
public class Navigator {
    private Set<int[]> paths;
    private Map<Integer, Integer> previousPath;
    public Navigator() {
        this.paths = new HashSet<>();
        this.previousPath = new HashMap<>();
    }

    public Move pickNextMove(int n, List<Move> possibleMoves) {
        Integer previous = previousPath.get(n);
        if(previous==null){
            previousPath.put(n,  previous = -1);
        }
        int next = previous+1;
        if(next>=possibleMoves.size()){
            next=0;
        }
        previousPath.put(n, next);
        return possibleMoves.get(next);
    }
}
