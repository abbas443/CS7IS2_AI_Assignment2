public class RBFSNode {

    public RBFSNode down, left, top, right, parent;

    public char[][] puzzle;

    public int x, y, cost;

    public RBFSNode(char[][] puzzle, int x, int y, int newX, int newY, RBFSNode parent) {

        this.puzzle = new char[puzzle.length][];
        for (int i = 0; i < puzzle.length; i++)
            this.puzzle[i] = puzzle[i].clone();

        this.parent = parent;
        this.down = null;
        this.left = null;
        this.top = null;
        this.right = null;

        this.puzzle[x][y] = this.puzzle[newX][newY];
        this.puzzle[newX][newY] = '0';

        this.cost = Integer.MAX_VALUE;
        this.x = newX;
        this.y = newY;
    }
    }


