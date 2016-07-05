package sandkev.knight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by kevin on 4/07/2016.
 */
public class Controller {

    private final KeyPad keyPad;
    public Controller() {
        this.keyPad = new KeyPad();
    }

    public long countPaths(int length, char value){
        return countPathsIteratively(value, 0, length);
        //return countPathsRecursively(value, 0, length, NodeTree.isVowel(value) ? 1 : 0);
    }

    private long countPathsRecursively(char value, int depth, int maxDepth, int vowelCount){
        if(depth==maxDepth){
            return 1;
        }else {
            long pathCount = 0;
            for (char child : keyPad.charsFor(value)) {
                int currentVowelCount = vowelCount;
                if(NodeTree.isVowel(child)){
                    currentVowelCount++;
                }
                if(currentVowelCount<=NodeTree.MAX_VOWEL_COUNT){
                    pathCount+= countPathsRecursively(child, depth + 1, maxDepth, currentVowelCount);
                }
            }
            if(pathCount>0 && pathCount%50000000==0){
                System.err.println("counted upto " + pathCount);
            }
            return pathCount;
        }
    }

    private long countPathsIteratively(char value, int depth, int maxDepth){

        Stack<Object[]> stack = new Stack<>();
        long count = 0;
        stack.push(fillTuple(depth, value, NodeTree.isVowel(value)?1:0));
        while(!stack.isEmpty()){
            Object[] tuple = stack.pop();
            int currentDepth = (int)tuple[0];
            char node = (char)tuple[1];
            int vowelCount = (int)tuple[2];
            if(currentDepth==maxDepth){
                count++;
                if(count%50000000==0){
                    System.err.println("counted upto " + count + " stackSize=" + stack.size());
                }
            }else if(currentDepth>=0){
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
        try{
            InputStreamReader streamReader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(streamReader);

            String input;
            int length = 10;
            char initalKey = 'A';
            System.out.println("Please enter the sequence length eg 10|16|32");
            NUMBER:while((input=br.readLine())!=null){
                boolean valid = true;
                for (char c : input.toCharArray()) {
                    if(c<'0' || c>'9'){
                        valid = false;
                    }
                }
                if(!valid){
                    System.out.println("a valid number is required");
                }else {
                    length = Integer.parseInt(input);
                    break NUMBER;
                }
            }
            System.out.println("Please enter the initial key press eg A|B|C");
            LETTER:while((input=br.readLine())!=null){
                char value = input.toUpperCase().charAt(0);
                char[] charsFor = controller.keyPad.charsFor(value);
                if(charsFor==null || charsFor.length == 0){
                    System.out.println("a valid keypress is required");
                }else {
                    initalKey = value;
                    break LETTER;
                }
            }

            System.out.println(controller.countPaths(length, initalKey));
            System.exit(0);

        }catch(IOException io){
            io.printStackTrace();
        }

        System.exit(1);
    }

}
