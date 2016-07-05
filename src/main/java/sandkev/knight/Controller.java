package sandkev.knight;

import javafx.util.Pair;

import java.util.Stack;

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
            //System.out.println("from " + value + " = " + countPathsRecursively(new NodeTree(null, value), 0, length));
            System.out.println("from " + value + " = " + countPathsRecursively(value, 0, length, NodeTree.isVowel(value)?1:0));
            //System.out.println("from " + value + " = " + countPathsIteratively(new NodeTree(null, value), 0, length));
            //System.out.println("from " + value + " = " + countPathsIteratively(value, 0, length));
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

    private int countPathsRecursively(char value, int depth, int maxDepth, int vowelCount){
        if(depth==maxDepth){
            //System.out.println(parent.toString());
            return 1;
        }else {
            int pathCount = 0;
            for (char child : keyPad.charsFor(value)) {
                int currentVowelCount = vowelCount;
                if(NodeTree.isVowel(child)){
                    currentVowelCount++;
                }
                if(currentVowelCount<=NodeTree.MAX_VOWEL_COUNT){
                    pathCount+= countPathsRecursively(child, depth + 1, maxDepth, currentVowelCount);
                }
            }
            return pathCount;
        }
    }

    private int countPathsIteratively(NodeTree parent, int depth, int maxDepth){

        Stack<Pair<Integer,NodeTree>> stack = new Stack<>();
        int count = 0;
        if(parent == null) {
            return count;
        }
        Pair<Integer,NodeTree> top = new Pair<>(depth, parent);
        stack.push(top);
        while(!stack.isEmpty()){
            Pair<Integer,NodeTree> pair = stack.pop();
            int currentDepth = pair.getKey();
            NodeTree node = pair.getValue();
            if(currentDepth==maxDepth){
                count++;
            }else {
                char[] chars = keyPad.charsFor(node.getValue());
                node.add(chars);
                for (NodeTree child : node.getChildren()) {
                    Pair<Integer,NodeTree> childItem = new Pair<>(currentDepth+1, child);
                    stack.push(childItem);
                }
            }
        }
        return count;
    }

    private int countPathsIteratively(char value, int depth, int maxDepth){

        Stack<Object[]> stack = new Stack<>();
        int count = 0;
        stack.push(fillTuple(depth, value, NodeTree.isVowel(value)?1:0));
        while(!stack.isEmpty()){
            Object[] tuple = stack.pop();
            int currentDepth = (int)tuple[0];
            char node = (char)tuple[1];
            int vowelCount = (int)tuple[2];
            if(currentDepth==maxDepth){
                count++;
            }else {
                for (char child : keyPad.charsFor(node)) {
                    int currentVowelCount = vowelCount;
                    if(NodeTree.isVowel(child)){
                        currentVowelCount++;
                    }
                    if(currentVowelCount<=NodeTree.MAX_VOWEL_COUNT){
                        stack.push(fillTuple(currentDepth+1, child, currentVowelCount));
                    }
                }
            }
        }
        return count;
    }

    private Object[] fillTuple(int depth, char value, int vowelCount) {
        return new Object[]{depth,value,vowelCount};
    }

    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        System.out.println(controller.countPaths(10));;
        System.out.println(controller.countPaths(16));;
        System.out.println(controller.countPaths(32));;
        System.exit(0);
    }

}
