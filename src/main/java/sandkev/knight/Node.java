package sandkev.knight;

import java.util.List;

/**
 * Created by kevin on 02/07/2016.
 */
public class Node {
    private final char value;
    private final int depth;
    private final List<Node> children;

    public Node(char value, int depth, List<Node> children) {
        this.value = value;
        this.depth = depth;
        this.children = children;
    }

    public char getValue() {
        return value;
    }

    public int getDepth() {
        return depth;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (value != node.value) return false;
        if (depth != node.depth) return false;
        return !(children != null ? !children.equals(node.children) : node.children != null);

    }

    @Override
    public int hashCode() {
        int result = (int) value;
        result = 31 * result + depth;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

}
