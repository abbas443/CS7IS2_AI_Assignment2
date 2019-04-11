import java.util.Arrays;

public class Node {

    int mat[][];
    int x;
    int y;
    Node parent;
    int hash;

    public Node(int[][] mat, Node parent, int x, int y) {
        this.mat = mat;
        this.x = x;
        this.y = y;
        this.parent = parent;
        hash = Arrays.deepHashCode(mat);
    }

    @Override
    public boolean equals(Object arg0) {
        return Arrays.deepEquals(mat, ((Node)arg0).mat);
    }


    @Override
    public int hashCode() {
        return hash;
    }
}
