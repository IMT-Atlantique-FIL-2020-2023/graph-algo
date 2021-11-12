package GraphAlgorithms;

import AdjacencyList.UndirectedGraph;
import Nodes.UndirectedNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TP2 {

    /*Algorithme parcours_profondeur(G, s, visited)
        visited[v]=True
        Ecrire(v)
        pour chaque voisin v de u faire
            SI visited[v]=False ALORS
                parcours_profondeur(G, v, visited)
            FinSi
        FinPour
    FinAlgorithme*/

    /**
     * Algorithme du parcours en profondeur avec tableau de booleens
     * @param g     graphe à étudier
     * @param s     sommet de départ
     * @deprecated
     */
    public static void parcoursProfondeur(UndirectedGraph g, UndirectedNode s) {
        //Initialiser le tableau de booleans indiquant si un noeud a été parcouru
        boolean[] visited = new boolean[g.getNbNodes()];
        for(int node = 0; node < visited.length; node++) {
            visited[node] = false;
        }

        visiterParcoursProfondeur(s, visited);
    }

    /**
     * Algorithme du parcours en profondeur avec tableau de booeleens
     * @param s         sommet visité à cette itération
     * @param visited   tableau des noeuds parcourus
     * @deprecated
     */
    private static void visiterParcoursProfondeur(UndirectedNode s, boolean[] visited) {
        visited[s.getLabel()] = false;

        Map<UndirectedNode, Integer> neighbours = s.getNeighbours();

        for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
            if(!visited[neighbour.getKey().getLabel()])
                visiterParcoursProfondeur(s, visited);
        }

    }


    /**
     * Algorithme du parcours en profondeur à partir de l'algorithme ExplorerGraphe()
     * @param g     graphe à étudier
     * @return      ensemble des noeuds parcourus avec l'ordre conservé
     */
    public static Set<UndirectedNode> parcoursProfondeur2(UndirectedGraph g) {
        Set<UndirectedNode> visited = new HashSet<UndirectedNode>();

        for(UndirectedNode sommet : g.getNodes()) {
            if(!visited.contains(sommet)) {
                visiterParcoursProfondeur2(sommet, visited);
            }
        }

        return visited;
    }

    /**
     * Algorithme du parcours en profondeur à partir de l'algorithme ExplorerGraphe()
     * @param s         sommet visité à cette itération
     * @param visited   ensemble des noeuds parcourus avec l'ordre conservé
     */
    private static void visiterParcoursProfondeur2(UndirectedNode s, Set<UndirectedNode> visited) {
        visited.add(s);

        Map<UndirectedNode, Integer> neighbours = s.getNeighbours();

        for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
            if(!visited.contains(neighbour))
                visiterParcoursProfondeur2(neighbour.getKey(), visited);
        }
    }

}
