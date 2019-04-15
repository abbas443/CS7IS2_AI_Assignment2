public class RBFSNode {
    // Parent and children Nodes declaration
    public RBFSNode down, left, top, right, parent;

    // Puzzle declaration
    public char[][] puzzle;

    // Blank tile coordinates and cost declaration
    public int x, y, cost;

    // Node constructor
    public RBFSNode(char[][] puzzle, int x, int y, int newX, int newY, RBFSNode parent) {

        // clone parent's puzzle to child's puzzle
        this.puzzle = new char[puzzle.length][];
        for (int i = 0; i < puzzle.length; i++)
            this.puzzle[i] = puzzle[i].clone();

        // For each new node, set parent to parent and children to null
        this.parent = parent;
        this.down = null;
        this.left = null;
        this.top = null;
        this.right = null;

        // Swaps tiles
        this.puzzle[x][y] = this.puzzle[newX][newY];
        this.puzzle[newX][newY] = '0';

        // Sets the cost to max value and gets new x & y coordinates for the blank tile
        this.cost = Integer.MAX_VALUE;
        this.x = newX;
        this.y = newY;
    }
    }

