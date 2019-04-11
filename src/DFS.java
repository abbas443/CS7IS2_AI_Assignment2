import java.util.*;

class DFS
{
    static int row[] = { 1, 0, -1, 0 };
    static int col[] = { 0, -1, 0, 1 };
    static int counter = 0;
    static ArrayList<Node> reversedList = new ArrayList<>();

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
        while (root != null)
        {
            reversedList.add(root);
            root = root.parent;
            counter++;
        }

        for (int i = reversedList.size() -1 ; i >= 0; i--)
        {
            printMatrix(reversedList.get(i).mat);
            System.out.println();
        }
    }

    public static boolean isSafe(int x, int y)
    {
        return (x >= 0 && x < 3 && y >= 0 && y < 3);
    }

    public static void solve(int init[][], int x, int y, int fin[][])
    {
        Node root = new Node(init, null, x, y);
        Stack<Node> st = new Stack<>();
        Set<Node> visited = new HashSet<>(); // used for backtracking
        st.push(root);

        while(!st.isEmpty())
        {
            Node top = st.pop();
            boolean backtrack = visited.contains(top);
            visited.add(top);

            if(Arrays.deepEquals(top.mat, fin))
            {
                printPath(top);
                break;
            }
            if(!backtrack) {
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

                        Node child = new Node(tempMatrix, top, newX, newY);
                        st.push(child);

                    }
                }
            }
        }
    }
    public static void main(String[] args) {

        boolean found = false;
        int x = 2, y = 2;
        int init[][] = {
                {1,2,3},
                {0,4,5},
                {7,8,6}};
        int fin[][] = {
                {1,2,3},
                {4,5,6},
                {7,8,0}};

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (init[i][j] == 0) {
                    x = i;
                    y = j;
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }
        solve(init, x, y, fin);
        System.out.println("Number of steps required: " + (counter-1));
    }
}
