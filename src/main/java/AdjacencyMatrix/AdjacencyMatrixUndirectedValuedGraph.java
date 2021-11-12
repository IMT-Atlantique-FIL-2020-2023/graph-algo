package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.UndirectedNode;

import java.util.List;

public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    private int[][] matrixCosts;    // The graph with Costs

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyMatrixUndirectedValuedGraph(int[][] mat, int[][] matrixVal) {
        super();
        this.order = mat.length;
        this.matrix = new int[this.order][this.order];
        this.matrixCosts = new int[this.order][this.order];
        for (int i = 0; i < this.order; i++) {
            for (int j = i; j < this.order; j++) {
                int val = mat[i][j];
                int cost = matrixVal[i][j];
                this.matrix[i][j] = val;
                this.matrix[j][i] = val;
                this.matrixCosts[i][j] = cost;
                this.matrixCosts[j][i] = cost;
                this.m += val;
            }
        }
    }


    //--------------------------------------------------
    // 					Accessors
    //--------------------------------------------------

    /**
     * @return the matrix with costs of the graph
     */
    public int[][] getMatrixCosts() {
        return matrixCosts;
    }

    // ------------------------------------------------
    // 					Methods
    // ------------------------------------------------

    /**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
    @Override
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
        super.removeEdge(x, y);
        // A completer
        if (!super.isEdge(x, y)) {
            this.matrixCosts[x.getLabel()][y.getLabel()] = 0;
            this.matrixCosts[y.getLabel()][x.getLabel()] = 0;
        }
    }

    /**
     * adds the edge (x,y,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        super.addEdge(x, y);
        // A completer
        if (this.matrixCosts[x.getLabel()][y.getLabel()] == 0 && this.matrixCosts[y.getLabel()][x.getLabel()] == 0) {
            this.matrixCosts[x.getLabel()][y.getLabel()] = cost;
            this.matrixCosts[y.getLabel()][x.getLabel()] = cost;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(super.toString() + "\n Matrix of Costs: \n");
        for (int[] matrixCost : this.matrixCosts) {
            for (int i : matrixCost) {
                s.append(i).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrix, matrixValued);
        System.out.println(am);
        // A completer
        System.out.println("N = " + am.getNbNodes() + "\n M = " + am.getNbEdges());
        List<Integer> t2 = am.getNeighbours(new UndirectedNode(2));
        for (Integer integer : t2) {
            System.out.print(integer + ", ");
        }
        System.out.println(am.isEdge(new UndirectedNode(2), new UndirectedNode(5)));
        for (int i = 0; i < 3; i++)
            am.addEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(am);
        am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        System.out.println(am);
        // A completer
        assert am.isEdge(new UndirectedNode(2), new UndirectedNode(5));
        for (int i = 0; i < 5; i++) {
            am.removeEdge(new UndirectedNode(2), new UndirectedNode(5));
        }

        assert !am.isEdge(new UndirectedNode(2), new UndirectedNode(5));
        assert am.matrixCosts[new UndirectedNode(2).getLabel()][new UndirectedNode(5).getLabel()] == 0;
    }

}
