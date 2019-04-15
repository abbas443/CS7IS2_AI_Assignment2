
import java.util.*;

class AStar
{
    static int row[] = { 1, 0, -1, 0 };
    static int col[] = { 0, -1, 0, 1 };
    static int counter = 0;
    static int level = 0;

    public static void printMatrix(int mat[][])
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
                System.out.print(mat[i][j]);
            System.out.println();
        }
    }

    public static void printPath(Node root)
    {
        if (root == null)
            return;
        printPath(root.parent);
        printMatrix(root.mat);

        counter++;
        System.out.println();
    }

    public static int calculateH(Node currentNode)
    {
        int n = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(currentNode.mat[i][j] != 0)
                    if(currentNode.mat[i][j] != State.fin[i][j])
                        n++;
            }
        }
        return n;
    }

    public static boolean isSafe(int x, int y)
    {
        return (x >= 0 && x < 3 && y >= 0 && y < 3);
    }

    public static void solve(int init[][], int x, int y, int fin[][])
    {
        Node root = new Node(init, null, x, y, level, level, -1, -1);
        List<Node> list = new ArrayList<>();
        Set<Node> visited = new HashSet<>(); // used to find visited node
        list.add(root);
        Node top;

        root.h = calculateH(root);
        root.f = root.g + root.h;

        while(true)
        {
            int min;

            min = list.get(0).f;


            top = list.get(0);

            for(Node n : list)
            {
                if(n.f < min) {
                    min = n.f;
                    top = n;
                }
            }
            visited.add(top);


            if(Arrays.deepEquals(top.mat, fin))
            {

                printPath(top);
                System.out.println("Nodes Expanded: " + visited.size());
                break;
            }

            ++level;
            list = new ArrayList<>();

            for (int i = 3; i >= 0; i--) {
                if (isSafe(top.x + row[i], top.y + col[i])) {
                    int tempMatrix[][] = new int[3][3];
                    for (int j = 0; j < 3; j++)
                        for (int k = 0; k < 3; k++)
                            tempMatrix[j][k] = top.mat[j][k];
                    int newX = top.x + row[i];
                    int newY = top.y + col[i];

                    int temp = tempMatrix[top.x][top.y];
                    tempMatrix[top.x][top.y] = tempMatrix[newX][newY];
                    tempMatrix[newX][newY] = temp;

                    Node child = new Node(tempMatrix, top, newX, newY, level, level, -1, -1);
                    child.h = calculateH(child);
                    child.f = child.g + child.h;
                    if(!visited.contains(child))
                        list.add(child);

                }
            }
        }
    }
    public static void main(String[] args) {

        boolean found = false;
        int x = 2, y = 2;

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (State.init[i][j] == 0) {
                    x = i;
                    y = j;
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }
        long startTime = System.nanoTime();

        solve(State.init, x, y, State.fin);

        long endTime = System.nanoTime();

        double durationInNano = (endTime - startTime);  //Total execution time in nano seconds

        double durationInMilli = durationInNano/1000000;  //Total execution time in milli seconds

        System.out.println("Number of steps required: " + (counter-1));
        System.out.println("Time Taken: " + durationInMilli + " ms");
    }
}
