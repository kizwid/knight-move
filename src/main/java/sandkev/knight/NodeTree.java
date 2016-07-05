package sandkev.knight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 04/07/2016.
 */
public class NodeTree {
    public static final int MAX_VOWEL_COUNT = 2;
    private final Character value;
    private final NodeTree parent;
    private final List<NodeTree> children;
    private final int vowelCount;
    public NodeTree(NodeTree parent, Character value){
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>(6);
        int vowel = isVowel(value)?1:0;
        this.vowelCount = parent==null?vowel:parent.vowelCount+vowel;
    }
    public List<NodeTree> add(char[] values){
        for (char value : values) {
            int currentVowelCount = vowelCount;
            if(isVowel(value)){
                currentVowelCount++;
            }
            if(currentVowelCount<=MAX_VOWEL_COUNT){
                children.add(new NodeTree(this, value));
            }
        }
        return children;
    }
    public static boolean isVowel(char c) {
        return c=='A'||c=='E'||c=='I'||c=='O'||c=='U';
    }

    public Character getValue() {
        return value;
    }

    public NodeTree getParent() {
        return parent;
    }

    public List<NodeTree> getChildren() {
        return children;
    }

    //display the sequence of key presses in the current node
    public String toString(){
        StringBuilder sb = new StringBuilder();
        NodeTree current = this;
        while (current.getParent()!=null){
            sb.append(current.getValue());
            current=current.getParent();
        }
        sb.reverse();
        validate(sb.toString());
        return sb.toString();
    }
    private void validate(String s) {
        int vowels = 0;
        for (char c : s.toCharArray()) {
            if(NodeTree.isVowel(c)){
                vowels++;
            }
        }
        if(vowels>MAX_VOWEL_COUNT){
            throw new IllegalStateException("too many vowels " + s);
        }
    }

}
