import java.util.*;

class BiDirectional
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
        System.out.println();
    }

    public static ArrayList<Node> printPath(Node root)
    {
        while (root != null)
        {
            reversedList.add(root);
            root = root.parent;
            counter++;
        }

        ArrayList<Node> list = new ArrayList<>();

        for (int i = reversedList.size() -1 ; i >= 0; i--)
        {
            printMatrix(reversedList.get(i).mat);
            list.add(reversedList.get(i));
        }

        return reversedList;
    }


    public static boolean isSafe(int x, int y)
    {
        return (x >= 0 && x < 3 && y >= 0 && y < 3);
    }

    public static void solve(int init[][], int fin[][], int x1, int y1, int x2, int y2) {
        Node root1 = new Node(init, null, x1, y1);
        Node root2 = new Node(fin, null, x2, y2);

        Stack<Node> st1 = new Stack<>();
        Stack<Node> st2 = new Stack<>();
        Set<Node> visited1 = new HashSet<>(); // used for backtracking
        Set<Node> visited2 = new HashSet<>(); // used for backtracking

        st1.push(root1);
        st2.push(root2);

        while (!st1.isEmpty() && !st2.isEmpty()) {
            Node top1 = st1.pop();
            Node top2 = st2.pop();

            boolean backtrack1 = visited1.contains(top1);
            boolean backtrack2 = visited2.contains(top2);

            visited1.add(top1);
            visited2.add(top2);

            if (Arrays.deepEquals(top1.mat, top2.mat)) {
                printPath(top1);
                System.out.println("___________________________");
//
                printPath(top2);

                System.out.println("Nodes Expanded: " + (visited1.size() + visited2.size()));

//                printBiDirectionalPath(top2);
                break;
            }
            if (!backtrack1) {
                for (int i = 3; i >= 0; i--) {
                    if (isSafe(top1.x + row[i], top1.y + col[i])) {
                        int tempMatrix[][] = new int[3][3];
                        for (int j = 0; j < 3; j++)
                            for (int k = 0; k < 3; k++)
                                tempMatrix[j][k] = top1.mat[j][k];
                        int newX = top1.x + row[i];
                        int newY = top1.y + col[i];

                        int temp = tempMatrix[top1.x][top1.y];
                        tempMatrix[top1.x][top1.y] = tempMatrix[newX][newY];
                        tempMatrix[newX][newY] = temp;

                        Node child = new Node(tempMatrix, top1, newX, newY);
                        st1.push(child);

                    }
                }
            }

            if (!backtrack2) {
                for (int i = 3; i >= 0; i--) {
                    if (isSafe(top2.x + row[i], top2.y + col[i])) {
                        int tempMatrix[][] = new int[3][3];
                        for (int j = 0; j < 3; j++)
                            for (int k = 0; k < 3; k++)
                                tempMatrix[j][k] = top2.mat[j][k];
                        int newX = top2.x + row[i];
                        int newY = top2.y + col[i];

                        int temp = tempMatrix[top2.x][top2.y];
                        tempMatrix[top2.x][top2.y] = tempMatrix[newX][newY];
                        tempMatrix[newX][newY] = temp;

                        Node child = new Node(tempMatrix, top2, newX, newY);
                        st2.push(child);

                    }
                }
            }

        }
    }


    public static void main(String[] args) {

        boolean found = false;
        int x1 = 2, y1 = 2;
        int x2 = 2, y2 = 2;


        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (State.init[i][j] == 0) {
                    x1 = i;
                    y1 = j;
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (State.fin[i][j] == 0) {
                    x2 = i;
                    y2 = j;
                    found = true;
                    break;
                }
            }
            if(found)
                break;
        }
        long startTime = System.nanoTime();

        solve(State.init, State.fin, x1, y1, x2, y2);

        long endTime = System.nanoTime();

        long durationInNano = (endTime - startTime);  //Total execution time in nano seconds

        double durationInMilli = durationInNano/1000000;  //Total execution time in milli seconds

        System.out.println("Number of steps required: " + (counter-1));
        System.out.println("Time Taken: " + durationInMilli + " ms");
    }
}
