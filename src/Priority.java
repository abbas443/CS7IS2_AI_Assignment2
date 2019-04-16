
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mti
 */
public class Priority {
   static int[][] preState;//keeps the previous state
    static Node2 neighbors_nodeArray[];

    //takes an node array, sort nodes based on distance of fn
    //and return lowest fn node
    public static Node2 sort(Node2[] nodeArray) {
        
        if(preState!=null){//parent exists
            nodeArray = getParentRemovedNodeArray(nodeArray, preState);//remove parent
        }
        
        //sorting nodes based on fn
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = nodeArray.length - 1; j > i; j--) {
                if (nodeArray[j].fn < nodeArray[j - 1].fn) {
                    Node2 temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j - 1];
                    nodeArray[j - 1] = temp;
                }
            }
        }
        Priority.neighbors_nodeArray = nodeArray;
        return nodeArray[0];
    }

    //takes node array and prestate 
    //remove the neighbor which same as prestate and return parent removed node array
    public static Node2[] getParentRemovedNodeArray(Node2 []nodeArray, int[][] preState) {
        Node2[] parentRemovedNodeArray = new Node2[nodeArray.length - 1];
        int j = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (Arrays.deepEquals(nodeArray[i].state, preState)) {
                //System.out.println("removed parent");
            } else {
                parentRemovedNodeArray[j] = nodeArray[i];
                j++;
            }
        }
        return parentRemovedNodeArray;
    }
}

//Node class
class Node2 {

    int fn;//fn value
    int[][] state;//states
    int [][] parent;
    public Node2(int fn, int[][] state, int[][]parent) {
        this.fn = fn;
        this.state = state;
        this.parent = parent;
    }
}
