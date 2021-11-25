package GraphAlgorithms;

import AdjacencyList.UndirectedValuedGraph;
import Collection.Pair;
import Nodes.UndirectedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TP4Prim {

    // ------------------------------------------
    // 	  Algorithme de PRIM
    // ------------------------------------------

    /**
     * Exécution de l'algorithme de PRIM dans un graphe connexe, valué et non orienté
     * Version se basant sur les noeuds, ne renvoie que le coût total
     * RQ : ne fonctionne pas sur les graphes non connexes. Le cas avec le graphe non connexe n'a pas pu être
     * testé et implémenté correctement, la génération aléatoire ne créant pas de voisins au premier sommet
     *
     * @param graph graphe à étudier avec l'algorithme de PRIM
     * @return coût total du parcours avec l'algorithme de PRIM
     * @deprecated la version suivante se base sur les arêtes pour avoir un retour plus complet à exploiter
     */
    public static int algorithmePrimNodes(UndirectedValuedGraph graph) {
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
        for (int iteration = 1; iteration < graph.getNbNodes(); iteration++) {
            //choisir la branche avec un poids minimum
            AMinAll = primChercherPoidsMinNodes(A);


            //Détecter si le graphe est non connexe
            if (AMinAll.size() == 0) {
                isNotConnectedGraph = true;
                break;
            }

            //Choisir le coût minimum pour cette itération
            costMin = Integer.MAX_VALUE;
            nodeMin = null;
            for (Map.Entry<UndirectedNode, Integer> nodeCostEntry : AMinAll.entrySet()) {
                if (nodeCostEntry.getValue() < costMin) {
                    costMin = nodeCostEntry.getValue();
                    nodeMin = nodeCostEntry.getKey();
                }
            }

            costTotal += costMin;
            A.add(nodeMin);
        }

        if (isNotConnectedGraph) {
            System.out.println("Algorithme de PRIM nodes partiel (le graph est non connexe) à partir du somme de départ : coût = " + costTotal + " et ordre de parcours : " + A);
        } else {
            System.out.println("Algorithme de PRIM nodes : coût = " + costTotal + " et ordre de parcours : " + A);
        }

        return costTotal;
    }

    /**
     * Etape de l'algorithme de PRIM pour détecter les branches de poids minimum à chaque itération
     *
     * @param A liste des noeuds déjà parcours
     * @return branches de poids minimum
     */
    private static HashMap<UndirectedNode, Integer> primChercherPoidsMinNodes(List<UndirectedNode> A) {

        HashMap<UndirectedNode, Integer> AMinAll = new HashMap<>(); //Map des noeuds voisins de tous les noeuds parcourus
        Map<UndirectedNode, Integer> AMinOne; //Map des noeuds voisins d'un noeud
        for (UndirectedNode nodeParcouru : A) {

            if (nodeParcouru != null && nodeParcouru.getNbNeigh() != 0) {
                AMinOne = nodeParcouru.getNeighbours();

                for (Map.Entry<UndirectedNode, Integer> nodeEntry : AMinOne.entrySet()) {
                    //On peut ignorer les voisins potentiels s'ils ont déjà été parcourus
                    if (!A.contains(nodeEntry.getKey())) {
                        //Si les voisins potentiels contiennent déjà un noeud à explorer, on compare quel coût serait le meilleur
                        if (AMinAll.containsKey(nodeEntry.getKey())) {
                            if (AMinAll.get(nodeEntry.getKey()) > nodeEntry.getValue())
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


    /**
     * Exécution de l'algorithme de PRIM dans un graphe connexe, valué et non orienté
     * Cette version se base sur les arêtes pour avoir un retour plus complet à exploiter
     * RQ : ne fonctionne pas sur les graphes non connexes. Le cas avec le graphe non connexe n'a pas pu être
     * testé et implémenté correctement, la génération aléatoire ne créant pas de voisins au premier sommet
     *
     * @param graph graphe à étudier avec l'algorithme de PRIM
     * @return coût total du parcours avec l'algorithme de PRIM associé à la liste des arêtes sélectionnées par l'algorithme
     * Les arêtes sont sous la forme <noeud de départ, noeud d'arrivée>
     */
    public static Pair<Integer, List<Pair<UndirectedNode, UndirectedNode>>> algorithmePrimEdges(UndirectedValuedGraph graph) {
        //Liste des sommets parcourus
        List<UndirectedNode> A = new ArrayList<>();
        //Ajouter le sommet de départ, ici on va prendre le 1er sommet de la liste des noeuds
        A.add(graph.getNodes().get(0));

        //Coût total des parcours
        int costTotal = 0;

        //Liste des arêtes choisies par l'algo de Prim (<noeud de départ, noued d'arrivée>)
        List<Pair<UndirectedNode, UndirectedNode>> listeAretes = new ArrayList<>();

        //Marqueur pour détecter si un graphe est non connexe
        boolean isNotConnectedGraph = false;

        //Comparaison des coûts à chaque itération pour choisir la branche avec un poids minimum
        Pair<UndirectedNode, UndirectedNode> areteMin;
        int costMin;
        HashMap<Pair<UndirectedNode, UndirectedNode>, Integer> AMinAll; //Map des noeuds voisins de tous les noeuds parcourus

        //On commence à 1 car on a déjà un premier sommet parcouru dans A
        //On s'arrête à n - 1 car lors de l'étape n-1, on va atteindre le dernier sommet
        for (int iteration = 1; iteration < graph.getNbNodes(); iteration++) {
            //choisir la branche avec un poids minimum
            AMinAll = primChercherPoidsMinEdges(A);


            //Détecter si le graphe est non connexe
            if (AMinAll.size() == 0) {
                isNotConnectedGraph = true;
                break;
            }

            //Choisir le coût minimum pour cette itération
            costMin = Integer.MAX_VALUE;
            areteMin = null;
            for (Map.Entry<Pair<UndirectedNode, UndirectedNode>, Integer> pairNodeCostEntry : AMinAll.entrySet()) {
                if (pairNodeCostEntry.getValue() < costMin) {
                    costMin = pairNodeCostEntry.getValue();
                    areteMin = pairNodeCostEntry.getKey();
                }
            }

            costTotal += costMin;
            assert areteMin != null;
            A.add(areteMin.getRight());
            listeAretes.add(areteMin);
        }

        if (isNotConnectedGraph) {
            System.out.println("Algorithme de PRIM edges partiel (le graph est non connexe) à partir du somme de départ : coût = " + costTotal + " et ordre de parcours : " + A + " et liste arêtes : " + listeAretes);
        } else {
            System.out.println("Algorithme de PRIM edges : coût = " + costTotal + " et ordre de parcours : " + A + " et liste arêtes : " + listeAretes);
        }
        for (Pair<UndirectedNode, UndirectedNode> aretes : listeAretes) {
            System.out.println("Arete {" + aretes.getLeft() + "," + aretes.getRight() + "}");
        }

        //return costTotal;
        return new Pair<>(costTotal, listeAretes);
    }

    /**
     * Etape de l'algorithme de PRIM pour détecter les branches de poids minimum à chaque itération
     *
     * @param A liste des noeuds déjà parcours
     * @return branches de poids minimum sous la forme <noeud de départ, noeud d'arrivée>
     */
    private static HashMap<Pair<UndirectedNode, UndirectedNode>, Integer> primChercherPoidsMinEdges(List<UndirectedNode> A) {

        HashMap<Pair<UndirectedNode, UndirectedNode>, Integer> AMinAll = new HashMap<>(); //Map des noeuds voisins de tous les noeuds parcourus
        Map<UndirectedNode, Integer> AMinOne; //Map des noeuds voisins d'un noeud
        for (UndirectedNode nodeParcouru : A) {

            if (nodeParcouru != null && nodeParcouru.getNbNeigh() != 0) {
                AMinOne = nodeParcouru.getNeighbours();

                for (Map.Entry<UndirectedNode, Integer> nodeEntry : AMinOne.entrySet()) {
                    //On peut ignorer les voisins potentiels s'ils ont déjà été parcourus
                    if (!A.contains(nodeEntry.getKey())) {
                        //Si les voisins potentiels contiennent déjà un noeud à explorer, on compare quel coût serait le meilleur
                        Pair<UndirectedNode, UndirectedNode> p = new Pair<>(nodeParcouru, nodeEntry.getKey());
                        if (AMinAll.containsKey(p)) {
                            if (AMinAll.get(p) > nodeEntry.getValue())
                                AMinAll.put(new Pair<>(nodeParcouru, nodeEntry.getKey()), nodeEntry.getValue());
                        } else {
                            AMinAll.put(new Pair<>(nodeParcouru, nodeEntry.getKey()), nodeEntry.getValue());
                        }
                    }
                }
            }
        }

        return AMinAll;
    }


    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateValuedGraphData(30, false, false, true, false, 100008);
        GraphTools.afficherMatrix(Matrix);
        UndirectedValuedGraph alUndirected = new UndirectedValuedGraph(Matrix);
        System.out.println(alUndirected);


        //Question 5 avec une complexité O(n²)
        int costAlgoPrimNodes = algorithmePrimNodes(alUndirected);

        Pair<Integer, List<Pair<UndirectedNode, UndirectedNode>>> costAlgoPrimEdges = algorithmePrimEdges(alUndirected);


    }
}
