import java.util.ArrayList;
import java.util.List;

public class RecursiveBestFirstSearch {

    int maxSteps = 0;

    private static final int size = 3;
    private static final Double INFINITY = Double.POSITIVE_INFINITY;

    int[] row = { 1, 0, -1, 0 };
    int[] column = { 0, -1, 0, 1 };

    char[][] goalState = { {'0','1','2'},
            {'3','4','5'},
            {'6','7','8'} };

    public void search(char[][] initPuzzle) {

        int[] coordinates = getBlankBoardPosn(initPuzzle);

        RBFSNode p = new RBFSNode(initPuzzle, coordinates[0], coordinates[1], coordinates[0], coordinates[1], null);
        RBFSNode n = p;

        RBFSResult sr = rbfs(p, n, (double) p.cost, INFINITY);

        printPath(sr.getSolution());

        if (sr.getOutcome() == RBFSResult.Result.SOLUTION_FOUND)
            System.out.println("Solution Found");
        else
            System.out.println("RBFS failed");

    }

    private RBFSResult rbfs(RBFSNode p, RBFSNode c, Double fNode, Double fLimit) {

        maxSteps++;

        if (c.cost == 0) return new RBFSResult(c, fLimit);

        List<RBFSNode> successors = expandNode(c);

        double[] f = new double[successors.size()];

        if (successors.size() == 0) return new RBFSResult(null, INFINITY);

        for (int s = 0; s < successors.size(); s++) {
            if (successors.get(s).cost < fNode)
                f[s] = Math.min(successors.get(s).cost, fNode);
            else
                f[s] = Math.max(successors.get(s).cost, fNode);
        }

        while (maxSteps < 1000) {

            int bestIndex = getBestFValueIndex(f);
            if (f[bestIndex] > fLimit)
                return new RBFSResult(null, f[bestIndex]);

            int altIndex = getNextBestFValueIndex(f, bestIndex);
            RBFSResult sr = rbfs(p, successors.get(bestIndex), f[bestIndex], Math.min(fLimit, f[altIndex]));
            f[bestIndex] = sr.getFCostLimit();

            if (sr.getOutcome() == RBFSResult.Result.SOLUTION_FOUND) {
                return sr;
            }
        }

        return new RBFSResult(null, INFINITY);
    }

    private int heuristic(char[][] initPuzzle, char[][] goal) {

        int value = 0;
        int size = initPuzzle.length;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (initPuzzle[i][j] != '0' && initPuzzle[i][j] != goal[i][j])
                    value = value + calculateManhattanDistance(initPuzzle, i, j);

        return value;
    }

    private int calculateManhattanDistance(char[][] puzzle, int x, int y) {

        int n = puzzle.length;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (puzzle[i][j] != '0' && puzzle[x][y] == goalState[i][j])
                    return Math.abs(x - i) + Math.abs(y - j);

        return 1;
    }

    private int[] getBlankBoardPosn(char[][] initPuzzle) {
        int[] coordinates = {0,0};
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if (initPuzzle[i][j] == '0') {
                    coordinates[0] = i;
                    coordinates[1] = j;
                }

        return coordinates;
    }

    private void printPath(RBFSNode root) {
        if (root == null) { return; }
        printPath(root.parent);
        printPuzzle(root.puzzle);
        System.out.println();
    }

    private void printPuzzle(char[][] Puzzle) {
        for (int i = 0; i < Puzzle.length; i++) {
            for (int j = 0; j < Puzzle.length; j++)
                System.out.print(Puzzle[i][j] + " ");
            System.out.println();
        }
    }

    private boolean isSafe(int x, int y) {
        return (x >= 0 && x < size && y >= 0 && y < size);
    }

    private List<RBFSNode> expandNode(RBFSNode n) {

        List<RBFSNode> nl = new ArrayList<RBFSNode>();

        for (int i = 0; i < 4; i++)
            if (isSafe(n.x + row[i], n.y + column[i])) {
                switch (i) {

                    case 0:
                        n.down = new RBFSNode(n.puzzle, n.x, n.y, n.x + row[i], n.y + column[i], n);
                        n.down.cost = heuristic(n.down.puzzle, goalState);
                        nl.add(n.down);
                        break;

                    case 1:
                        n.left = new RBFSNode(n.puzzle, n.x, n.y, n.x + row[i], n.y + column[i], n);
                        n.left.cost = heuristic(n.left.puzzle, goalState);
                        nl.add(n.left);
                        break;

                    case 2:
                        n.top = new RBFSNode(n.puzzle, n.x, n.y, n.x + row[i], n.y + column[i], n);
                        n.top.cost = heuristic(n.top.puzzle, goalState);
                        nl.add(n.top);
                        break;

                    case 3:
                        n.right = new RBFSNode(n.puzzle, n.x, n.y, n.x + row[i], n.y + column[i], n);
                        n.right.cost = heuristic(n.right.puzzle, goalState);
                        nl.add(n.right);
                        break;
                }
            }

        return nl;
    }

    private int getBestFValueIndex(double[] f) {
        int lidx = 0;
        Double lowestSoFar = INFINITY;

        for (int i = 0; i < f.length; i++) {
            if (f[i] < lowestSoFar) {
                lowestSoFar = f[i];
                lidx = i;
            }
        }

        return lidx;
    }

    private int getNextBestFValueIndex(double[] f, int bestIndex) {
        int lidx = bestIndex;
        Double lowestSoFar = INFINITY;

        for (int i = 0; i < f.length; i++) {
            if (i != bestIndex && f[i] < lowestSoFar) {
                lowestSoFar = f[i];
                lidx = i;
            }
        }

        return lidx;
    }
}

class RBFSResult {
    public enum Result {
        FAILURE, SOLUTION_FOUND
    };

    private RBFSNode solution;

    private Result outcome;

    private final Double fCostLimit;

    public RBFSResult(RBFSNode solution, Double fCostLimit) {
        if (null == solution) {
            this.outcome = Result.FAILURE;
        } else {
            this.outcome = Result.SOLUTION_FOUND;
            this.solution = solution;
        }
        this.fCostLimit = fCostLimit;
    }

    public Result getOutcome() {
        return outcome;
    }

    public RBFSNode getSolution() {
        return solution;
    }

    public Double getFCostLimit() {
        return fCostLimit;
    }


    public static void main(String[] args) {

        RecursiveBestFirstSearch rbfs = new RecursiveBestFirstSearch();

        char[][] puzzle = { {'2','5','3'}, {'1','6','0'}, {'7','8','4'}  };

        rbfs.search(puzzle);
    }

}
