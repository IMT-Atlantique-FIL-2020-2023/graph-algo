package GraphAlgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import AdjacencyList.DirectedValuedGraph;
import Collection.Pair;
import Nodes.DirectedNode;

public final class Bellman {

    /**
     * Algorithme de Bellman, calcul des chemins les plus courts depuis un sommet vers tous les autres sommets
     * @param graph     graphe à explorer
     * @param source    sommet de départ
     * @return map des distances depuis la source vers chaque sommet sous la forme {noeud à tester, [prédecesseur, coût pour atteindre le sommet]}
     * @throws Exception
     */
    public static HashMap<DirectedNode, Pair<DirectedNode, Integer>> calculerCheminsPlusCourtDepuisSource(DirectedValuedGraph graph, DirectedNode source) throws Exception {

        // Distance depuis la source à chaque noeud, avec prédécesseur et coût
        HashMap<DirectedNode, Pair<DirectedNode, Integer>> distances = new HashMap<>();
        for (DirectedNode node : graph.getNodes()){
            distances.put(node, new Pair(new DirectedNode(-1), Integer.MAX_VALUE));
        }

        //Atteindre soi-même coûte 0 au minimum (sauf dans le cas d'un graphe avec des valeurs impaires)
        distances.put(source, new Pair(source, 0));

        //boucle principale
        //On répète la boucle pour n-1 noeuds
        for (int i = 1; i < graph.getNbNodes()-1; i++){
            //On teste si on peut réduire le coût pour atteindre chaque sommet lors de cette itération
            for(int j = 0; j < graph.getNbNodes()-1; j++){

                //On teste tous les prédecesseurs du sommet parcouru
                for(Entry<DirectedNode,Integer> edge : graph.getNodes().get(j).getPreds().entrySet()){
                    if(distances.get(graph.getNodes().get(j)).getRight() + edge.getValue() < distances.get(edge.getKey()).getRight()){
                        distances.put(edge.getKey(), new Pair<DirectedNode,Integer>(graph.getNodes().get(j), distances.get(graph.getNodes().get(j)).getRight() + edge.getValue()));
                    }
                }
            }
        }

        //Dernière itération pour vérifier l'absence de cycle négatif
        for(int j = 0;j<graph.getNbNodes()-1;j++){
            for(Entry<DirectedNode,Integer> edge:graph.getNodes().get(j).getPreds().entrySet()){
                if(distances.get(graph.getNodes().get(j)).getRight() + edge.getValue() < distances.get(edge.getKey()).getRight()){
                    throw new Exception("Algorithme de Bellman, cycle négatif détecté.");
                }
            }
        }

        return distances;
    }

    /**
     * Algorithme de Bellman, détecter les plus courts chemins entre deux sommets d'un graphe
     * @param graph         graphe à explorer
     * @param source        sommet de départ
     * @param destination   sommet à atteindre
     * @return Liste des sommets à parcourir pour obtenir le chemin le plus court de la source à la destination
     */
    public static Pair<List<DirectedNode>, Integer> calculerCheminPlusCourtEntreSourceEtDestination(DirectedValuedGraph graph, DirectedNode source, DirectedNode destination){
        //Par l'algorithme de Bellman, on va obtenir tous les plus courts sommets depuis la source vers les autres sommets
        HashMap<DirectedNode, Pair<DirectedNode, Integer>> bellmanChemins = new HashMap<DirectedNode, Pair<DirectedNode, Integer>>();
        try {
            bellmanChemins = calculerCheminsPlusCourtDepuisSource(graph, source);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinkedList<DirectedNode> cheminSommets = new LinkedList<>();
        cheminSommets.add(destination);

        int coutChemin = 0;

        DirectedNode temp = destination;
        int cpt = graph.getNodes().size();

        //Depuis la destination, on va aller de prédecesseurs en prédecesseurs pour retrouver le sommet de départ
        while(temp != source && cpt >=0){
            coutChemin += bellmanChemins.get(temp).getRight();
            temp = bellmanChemins.get(temp).getLeft();
            cheminSommets.add(temp);
            cpt--;
        }

        Pair<List<DirectedNode>, Integer> cheminPlusCourt = new Pair<>(cheminSommets, coutChemin);

        return cheminPlusCourt;
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        DirectedValuedGraph al = new DirectedValuedGraph(mat);
        System.out.println(al);

        try {
            System.out.println(calculerCheminsPlusCourtDepuisSource(al, al.getNodes().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(calculerCheminPlusCourtEntreSourceEtDestination(al, al.getNodes().get(0),al.getNodes().get(7)));
    }
}
