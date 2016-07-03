package sandkev.knight;

/**
 * Created by kevin on 4/07/2016.
 */
public class Controller {

    Board board;
    public Controller() {
        this.board = new Board();
    }

    public int countPaths(int length){

        for (Cell cell : board.getActiveCells()) {
            char value = cell.getValue();
            NodeTree root = new NodeTree(null, value);
            System.out.println("from " + value + " = " + countPaths(root, 0, length));
        }
        return 0;

/*
        Position position = new Position(0, 0);
        Cell cell = board.at(position);
        char value = cell.getValue();
        NodeTree root = new NodeTree(null, value);
        return countPaths(root, 0, length);
*/
    }

    private int countPaths(NodeTree parent, int depth, int maxDepth){
        if(depth==maxDepth){
            StringBuilder sb = new StringBuilder();
            NodeTree current = parent;
            while (current.parent!=null){
                sb.append(current.value);
                current=current.parent;
            }
            sb.reverse();
            //System.out.println(sb.toString());
            validate(sb.toString());
            return 1;
        }else {
            char[] chars = board.charsFor(parent.value);
            parent.add(chars);
            int pathCount = 0;
            for (NodeTree child : parent.children) {
                pathCount+=countPaths(child, depth + 1, maxDepth);
            }
            return pathCount;
        }
    }

    private void validate(String s) {
        int vowels = 0;
        for (char c : s.toCharArray()) {
            if(NodeTree.isVowel(c)){
                vowels++;
            }
        }
        if(vowels>2){
            throw new IllegalStateException("too many vowels " + s);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        System.out.println(controller.countPaths(10));;
        System.exit(0);
    }

}
