package AdjacencyList;

import java.util.ArrayList;
import java.util.Map;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Abstraction.IDirectedGraph;

/**
 * Graphs directed
 */
public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

    private static int _DEBBUG = 0;

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public DirectedGraph() {
        super();
        this.nodes = new ArrayList<DirectedNode>();
    }

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
                DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn, 0);
                    nn.getPreds().put(n, 0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for (DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
            DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn, 0);
                snn.getPreds().put(nn, 0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
        // A completer
        return getNodeOfList(from).getSuccs().containsKey(to) && getNodeOfList(to).getPreds().containsKey(from);
    }

    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        // A completer
        if (isArc(from, to)) {
            this.nodes.get(from.getLabel()).getSuccs().remove(to);
            this.nodes.get(to.getLabel()).getSuccs().remove(from);
        }
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
        // A completer
        this.nodes.get(from.getLabel()).addSucc(to, 0);
        this.nodes.get(to.getLabel()).addPred(from, 0);
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     *
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        DirectedGraph g = new DirectedGraph(this);
        // A completer

        Map<DirectedNode, Integer> successors;
        for (DirectedNode n : g.getNodes()) {
            successors = n.getSuccs();
            n.setSuccs(n.getPreds());
            n.setPreds(successors);
        }

        return g;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (DirectedNode n : nodes) {
            s.append("successors of ").append(n).append(" : ");
            for (DirectedNode sn : n.getSuccs().keySet()) {
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        // A completer

        IDirectedGraph alInversed = al.computeInverse();
        System.out.println(alInversed);
    }
}
