package sandkev.knight;

/**
 * Created by kevin on 4/07/2016.
 */
public class Controller {

    private final KeyPad keyPad;
    public Controller() {
        this.keyPad = new KeyPad();
    }

    public int countPaths(int length){

        for (Key key : keyPad.getActiveKeys()) {
            char value = key.getValue();
            NodeTree root = new NodeTree(null, value);
            System.out.println("from " + value + " = " + countPathsRecursively(root, 0, length));
            return 0;
        }
        return 0;

    }

    private int countPathsRecursively(NodeTree parent, int depth, int maxDepth){
        if(depth==maxDepth){
            //System.out.println(parent.toString());
            return 1;
        }else {
            char[] chars = keyPad.charsFor(parent.getValue());
            parent.add(chars);
            int pathCount = 0;
            for (NodeTree child : parent.getChildren()) {
                pathCount+= countPathsRecursively(child, depth + 1, maxDepth);
            }
            return pathCount;
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        System.out.println(controller.countPaths(16));;
        //System.out.println(controller.countPaths(14));;
        //System.out.println(controller.countPaths(15));;
        System.exit(0);
    }

}
