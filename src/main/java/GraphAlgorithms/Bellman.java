package GraphAlgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import Collection.Pair;
import Nodes.DirectedNode;

public final class Bellman {

    private Bellman() {}

    public static HashMap<DirectedNode, Pair<DirectedNode, Integer>> calculateShortestPathFromSource(DirectedGraph graph, DirectedNode source) throws Exception {

        // Distance depuis source à node, avec prédécesseur et value
        HashMap<DirectedNode, Pair<DirectedNode, Integer>> distances = new HashMap<>();
        for (DirectedNode node : graph.getNodes()){
            distances.put(node, new Pair(new DirectedNode(-1), 999999999));
        }

        distances.put(source, new Pair(source, 0));

        //boucle principale
        for (int i = 1; i< graph.getNbNodes()-1;i++){
            for(int j = 0;j<graph.getNbNodes()-1;j++){
                for(Entry<DirectedNode,Integer> edge:graph.getNodes().get(j).getPreds().entrySet()){
                    if(distances.get(graph.getNodes().get(j)).getRight() + edge.getValue() < distances.get(edge.getKey()).getRight()){
                        distances.put(edge.getKey(), new Pair<DirectedNode,Integer>(graph.getNodes().get(j), distances.get(graph.getNodes().get(j)).getRight() + edge.getValue()));
                    }
                }
            }
        }

        //vérification absence de cycle négatif
        for(int j = 0;j<graph.getNbNodes()-1;j++){
            for(Entry<DirectedNode,Integer> edge:graph.getNodes().get(j).getPreds().entrySet()){
                if(distances.get(graph.getNodes().get(j)).getRight() + edge.getValue() < distances.get(edge.getKey()).getRight()){
                    throw new Exception("cycle négatif");
                }
            }
        }

        return distances;
    }

    public static List<DirectedNode> getShortestPathFromSourceToDestination(DirectedGraph graph, DirectedNode source, DirectedNode destination){
        HashMap<DirectedNode, Pair<DirectedNode, Integer>> shortestPaths = new HashMap<DirectedNode, Pair<DirectedNode, Integer>>();
        try {
            shortestPaths = calculateShortestPathFromSource(graph, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DirectedNode> shortestPath = new LinkedList<DirectedNode>();
        shortestPath.add(destination);
        DirectedNode temp = destination;
        int cpt = graph.getNodes().size();
        while(temp != source && cpt >=0){
            temp = shortestPaths.get(temp).getLeft();
            shortestPath.add(temp);
            cpt--;
        }
        return shortestPath;
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        DirectedValuedGraph al = new DirectedValuedGraph(mat);
        System.out.println(al);

        try {
            System.out.println(calculateShortestPathFromSource(al, al.getNodes().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(getShortestPathFromSourceToDestination(al, al.getNodes().get(0),al.getNodes().get(7)));
    }
}
