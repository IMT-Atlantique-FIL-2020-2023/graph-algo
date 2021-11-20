package GraphAlgorithms;

import AdjacencyList.UndirectedValuedGraph;
import Nodes.UndirectedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TP4 {

    // ------------------------------------------
    // 	  Algorithme de PRIM
    // ------------------------------------------

    /**
     * Exécution de l'algorithme de PRIM dans un graphe connexe, valué et non orienté
     * RQ : ne fonctionne pas sur les graphes non connexes. Le cas avec le graphe non connexe n'a pas pu être
     * testé et implémenté correctement, la génération aléatoire ne créant pas de voisins au premier sommet
     *
     * @param graph graphe à étudier avec l'algorithme de PRIM
     * @return coût total du parcours avec l'algorithme de PRIM
     */
    public static int algorithmePrim(UndirectedValuedGraph graph) {
        List<UndirectedNode> A = new ArrayList<>();
        //Ajouter le sommet de départ, ici on va prendre le 1er sommet de la liste des noeuds
        A.add(graph.getNodes().get(0));

        int costTotal = 0;

        //Marqueur pour détecter si un graphe est non connexe
        boolean isNotConnectedGraph = false;

        //Comparaison des coûts à chaque itération pour choisir la branche avec un poids minimum
        UndirectedNode nodeMin;
        int costMin;
        HashMap<UndirectedNode, Integer> AMinAll; //Map des noeuds voisins de tous les noeuds parcourus

        //On commence à 1 car on a déjà un premier sommet parcouru dans A
        //On s'arrête à n - 1 car lors de l'étape n-1, on va atteindre le dernier sommet
        for(int iteration = 1; iteration < graph.getNbNodes(); iteration++) {
            //choisir la branche avec un poids minimum
            AMinAll = primChercherPoidsMin(A);


            //Détecter si le graphe est non connexe
            if(AMinAll.size() == 0) {
                isNotConnectedGraph = true;
                break;
            }

            //Choisir le coût minimum pour cette itération
            costMin = Integer.MAX_VALUE;
            nodeMin = null;
            for(Map.Entry<UndirectedNode, Integer> nodeCostEntry : AMinAll.entrySet()) {
                if(nodeCostEntry.getValue() < costMin) {
                    costMin = nodeCostEntry.getValue();
                    nodeMin = nodeCostEntry.getKey();
                }
            }

            costTotal += costMin;
            A.add(nodeMin);
        }

        if(isNotConnectedGraph) {
            System.out.println("Algorithme de PRIM partiel (le graph est non connexe) à partir du somme de départ : coût = " + costTotal + " et ordre de parcours : " + A);
        } else {
            System.out.println("Algorithme de PRIM : coût = " + costTotal + " et ordre de parcours : " + A);
        }

        return costTotal;
    }

    /**
     * Etape de l'algorithme de PRIM pour détecter les branches de poids minimum à chaque itération
     * @param A liste des noeuds déjà parcours
     * @return branches de poids minimum
     */
    private static HashMap<UndirectedNode, Integer> primChercherPoidsMin(List<UndirectedNode> A) {

        HashMap<UndirectedNode, Integer> AMinAll = new HashMap<>();; //Map des noeuds voisins de tous les noeuds parcourus
        HashMap<UndirectedNode, Integer> AMinOne; //Map des noeuds voisins d'un noeud
        for(UndirectedNode nodeParcouru : A) {

            if(nodeParcouru != null && nodeParcouru.getNbNeigh() != 0) {
                AMinOne = (HashMap) nodeParcouru.getNeighbours();

                for(Map.Entry<UndirectedNode, Integer> nodeEntry : AMinOne.entrySet()) {
                    //On peut ignorer les voisins potentiels s'ils ont déjà été parcourus
                    if(!A.contains(nodeEntry.getKey())) {
                        //Si les voisins potentiels contiennent déjà un noeud à explorer, on compare quel coût serait le meilleur
                        if(AMinAll.containsKey(nodeEntry.getKey())) {
                            if(AMinAll.get(nodeEntry.getKey()) > nodeEntry.getValue())
                                AMinAll.put(nodeEntry.getKey(), nodeEntry.getValue());
                        } else {
                            AMinAll.put(nodeEntry.getKey(), nodeEntry.getValue());
                        }
                    }
                }
            }
        }

        return AMinAll;
    }


    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateValuedGraphData(6, false, false, true, true, 100007);
        GraphTools.afficherMatrix(Matrix);
        UndirectedValuedGraph alUndirected = new UndirectedValuedGraph(Matrix);
        System.out.println(alUndirected);


        //Question 5
        int costAlgoPrim = algorithmePrim(alUndirected);


    }
}
