package GraphAlgorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import AdjacencyList.DirectedGraph;
import Nodes.DirectedNode;

public final class Dijkstra {

    /**
     * Algorithme de Dijkstra, calcul des chemins les plus courts depuis un sommet vers tous les autres sommets
     * @param graph     graphe à explorer
     * @param source    sommet de départ
     * @return
     */
    public static HashMap<DirectedNode, LinkedList<DirectedNode>> calculerCheminsPlusCourtDepuisSource(DirectedGraph graph, DirectedNode source) {

        //initialisation

        // Distance depuis la source à chaque noeud avec coût minimum
        HashMap<DirectedNode, Integer> distances = new HashMap<>();
        for(DirectedNode node : graph.getNodes()){
            distances.put(node, Integer.MAX_VALUE);
        }
        //Atteindre soi-même coûte 0 au minimum (sauf dans le cas d'un graphe avec des valeurs impaires)
        distances.put(source, 0);

        // Chemin le plus court entre la source et chaque noeud
        HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPaths = new HashMap<>();
        for(DirectedNode node : graph.getNodes()){
            shortestPaths.put(node,new LinkedList<>());
        }

        Set<DirectedNode> settledNodes = new HashSet<>();   //sommets dont toutes les possibilités ont été explorées
        Set<DirectedNode> unsettledNodes = new HashSet<>(); //sommets à explorer

        unsettledNodes.add(source);

        //Application de l'algorithme
        DirectedNode currentNode;   //noeud parcouru à chaque itération
        DirectedNode adjacentNode;  //un des successeur du noeud parcouru à comparer avec les autres
        while (unsettledNodes.size() != 0) {
            currentNode = chercherPredecesseurLePlusProche(unsettledNodes,distances);
            unsettledNodes.remove(currentNode);

            //Pour chaque successeur d'un noeud, on va comparer les coûts
            for (Entry<DirectedNode, Integer> adjacencyPair: currentNode.getSuccs().entrySet()) {
                adjacentNode = adjacencyPair.getKey();

                if (!settledNodes.contains(adjacentNode)) {
                    calculerDistanceMinimale(adjacentNode, adjacencyPair.getValue(), currentNode, distances, shortestPaths);
                    unsettledNodes.add(adjacentNode);
                }
            }

            settledNodes.add(currentNode);
        }

        return shortestPaths;
    }

    /**
     * A chaque itération, on va chercher à réduire si possible le coût à atteindre pour un noeud
     * @param evaluationNode    sucesseur du noeud à tester pour en réduire le coût
     * @param edgeWeigh         coût de l'arête entre le noeud et son prédecesseur
     * @param sourceNode        noeud parcouru à tester
     * @param distances         distance depuis la source à chaque noeud avec coût minimum
     * @param shortestPaths     Chemin le plus court entre la source et chaque noeud
     */
    private static void calculerDistanceMinimale(DirectedNode evaluationNode, Integer edgeWeigh, DirectedNode sourceNode,
                                                 HashMap<DirectedNode, Integer> distances, HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPaths) {
        Integer sourceDistance = distances.get(sourceNode);

        if ((sourceDistance + edgeWeigh) < distances.get(evaluationNode)) {
            distances.put(evaluationNode,sourceDistance + edgeWeigh);

            LinkedList<DirectedNode> shortestPath = new LinkedList<>(shortestPaths.get(sourceNode));
            shortestPath.add(sourceNode);
            shortestPaths.put(evaluationNode, shortestPath);
        }
    }

    /**
     * Chercher le prédecesseur avec le coût le moins élevé
     * @param unsettledNodes    reste des noeuds à parcourir dans l'algorithme
     * @param distances         ensemble des coûts pour atteindre chaque noeud
     * @return le prochain sommet à parcourir avec le coût le moins élevé
     */
    private static DirectedNode chercherPredecesseurLePlusProche(Set < DirectedNode > unsettledNodes, HashMap<DirectedNode, Integer> distances) {
        DirectedNode lowestDistanceNode = null; //Noeud avec le coût le moins élevé

        int lowestDistance = Integer.MAX_VALUE; //Coût du noeud le moins élevé dans la comparaison
        int nodeDistance; //noeud parcouru dans la comparaison

        //Comparaison des coûts
        for (DirectedNode node: unsettledNodes) {
            nodeDistance = distances.get(node);

            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Affichage du résultat de l'algorithme de Dijkstra pour avoir le plus court chemin entre la source et la destination
     * @param source        sommet de départ
     * @param destination   sommet à atteindre
     * @param shortestPath  liste des sommets constituant le chemin le plus court entre la source et la destination
     */
    public static void afficherCheminPlusCourt(DirectedNode source, DirectedNode destination, LinkedList<DirectedNode> shortestPath){
        System.out.println("Shortest path from "+ source.getLabel() + " to " + destination.getLabel() + ": " +shortestPath);
    }


    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 10, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        DirectedGraph al = new DirectedGraph(mat);


        for(DirectedNode src : al.getNodes()){
            HashMap<DirectedNode, LinkedList<DirectedNode>> shortestPath = calculerCheminsPlusCourtDepuisSource(al,src);
            for(DirectedNode dest : al.getNodes()){
                afficherCheminPlusCourt(src, dest, shortestPath.get(dest));
            }
        }
    }
}
