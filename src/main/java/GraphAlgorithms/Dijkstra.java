package GraphAlgorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import AdjacencyList.DirectedGraph;
import Nodes.DirectedNode;

public final class Dijkstra {

    private Dijkstra() {}

    public static HashMap<DirectedNode, LinkedList<DirectedNode>> calculateShortestPathFromSource(DirectedGraph graph, DirectedNode source) {

        //initialisation

        HashMap<DirectedNode, Integer> distances = new HashMap<>();
        for(DirectedNode node : graph.getNodes()){
            distances.put(node, Integer.MAX_VALUE);
        }
        HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPaths = new HashMap<>();
        for(DirectedNode node : graph.getNodes()){
            shortestPaths.put(node,new LinkedList<>());
        }
        distances.put(source, 0);
        Set<DirectedNode> settledNodes = new HashSet<>();
        Set<DirectedNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        //déroulement

        while (unsettledNodes.size() != 0) {
            DirectedNode currentNode = getLowestDistanceNode(unsettledNodes,distances);
            unsettledNodes.remove(currentNode);
            for (Entry < DirectedNode, Integer> adjacencyPair:
                    currentNode.getSuccs().entrySet()) {
                DirectedNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode, distances,shortestPaths);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return shortestPaths;
    }

    private static void CalculateMinimumDistance(DirectedNode evaluationNode,
                                                 Integer edgeWeigh, DirectedNode sourceNode, HashMap<DirectedNode, Integer> distances, HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPaths) {
        Integer sourceDistance = distances.get(sourceNode);
        if (sourceDistance + edgeWeigh < distances.get(evaluationNode)) {
            distances.put(evaluationNode,sourceDistance + edgeWeigh);
            LinkedList<DirectedNode> shortestPath = new LinkedList<>(shortestPaths.get(sourceNode));
            shortestPath.add(sourceNode);
            shortestPaths.put(evaluationNode, shortestPath);
        }
    }

    private static DirectedNode getLowestDistanceNode(Set < DirectedNode > unsettledNodes, HashMap<DirectedNode, Integer> distances) {
        DirectedNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (DirectedNode node: unsettledNodes) {
            int nodeDistance = distances.get(node);
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void printShortestPath(DirectedNode source, DirectedNode dest,LinkedList<DirectedNode> shortestPath){
        System.out.println("Shortest path from "+ source.getLabel() + " to " + dest.getLabel() + ": " +shortestPath);

    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 10, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        DirectedGraph al = new DirectedGraph(mat);


        for(DirectedNode src : al.getNodes()){
            HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPath = calculateShortestPathFromSource(al,src);
            for(DirectedNode dest : al.getNodes()){
                printShortestPath(src, dest, shortestPath.get(dest));
            }
        }
    }
}
