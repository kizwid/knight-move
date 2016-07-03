package sandkev.knight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 04/07/2016.
 */
public class NodeTree {
    private static final int MAX_VOWEL_COUNT = 2;
    Character value;
    NodeTree parent;
    List<NodeTree> children;
    int vowelCount;
    public NodeTree(NodeTree parent, Character value){
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>();
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

}
