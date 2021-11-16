package GraphAlgorithms;

import AdjacencyList.DirectedGraph;
import AdjacencyList.UndirectedGraph;
import Nodes.UndirectedNode;

import java.util.*;

public class TP2 {
    //Voir la partie CFC
    private static int compt;
    private static int[] debut;
    private static int[] fin;


    // ------------------------------------------
    // 	  Algorithme de parcours en profondeur
    // ------------------------------------------

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
     * @return      ensemble des noeuds parcourus avec l'ordre de parcours conservé
     */
    public static List<UndirectedNode> parcoursProfondeur2(UndirectedGraph g) {
        List<UndirectedNode> visited = new ArrayList<UndirectedNode>();

        for(UndirectedNode sommet : g.getNodes()) {
            if(!visited.contains(sommet)) {
                visiterParcoursProfondeur2(sommet, visited, 0);
            }
        }

        return visited;
    }

    /**
     * Algorithme du parcours en profondeur à partir de l'algorithme ExplorerGraphe()
     * @param s             sommet visité à cette itération
     * @param visited       ensemble des noeuds parcourus avec l'ordre de parcours conservé
     * @param profondeur    étage actuel de l'arbre construit à partir du graphe
     */
    private static void visiterParcoursProfondeur2(UndirectedNode s, List<UndirectedNode> visited, int profondeur) {
        System.out.println("Profondeur : " + profondeur + ", noeud : " + s);
        visited.add(s);

        /*Map<UndirectedNode, Integer> neighbours = s.getNeighbours();

        for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
            if(!visited.contains(neighbour.getKey()))
                visiterParcoursProfondeur2(neighbour.getKey(), visited);
        }*/

        if( s.getNbNeigh() > 0 ) { // Si il n'y a plus de de fils disponible, Je remonte d'un cran
            Map<UndirectedNode, Integer> neighbours = s.getNeighbours(); // Sinon je récupère la liste des fils

            for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
                if(!visited.contains(neighbour.getKey()))
                    visiterParcoursProfondeur2(neighbour.getKey(), visited, profondeur + 1);
            } // Et je continue d'explorer mon arbre
        }
    }


    // ------------------------------------------
    // 	  Algorithme de parcours en largeur
    // ------------------------------------------

    /**
     * Algorithme du parcours en largeur
     * @param g     graphe à étudier
     * @param s     noeud de départ
     * @return      ensemble des noeuds parcourus avec l'ordre de parcours conservé
     */
    public static List<UndirectedNode> parcoursLargeur(UndirectedGraph g, UndirectedNode s) {
        //Initialiser le tableau de booleans indiquant si un noeud a été parcouru
        boolean[] visited = new boolean[g.getNbNodes()];

        for(int node = 0; node < visited.length; node ++)
            visited[node] = false;
        visited[s.getLabel()] = true;

        //Conserver l'ordre de parcours des nodes
        List<UndirectedNode> orderNodes = new ArrayList<>();

        //FIFO
        ArrayList<UndirectedNode> toVisit = new ArrayList<>();
        toVisit.add(s);

        //Parcours du graphe
        UndirectedNode sommetParcouru;
        Map<UndirectedNode, Integer> neighbours;

        while (!toVisit.isEmpty()) {
            sommetParcouru = toVisit.get(0);
            toVisit.remove(0);
            orderNodes.add(sommetParcouru);

            neighbours = sommetParcouru.getNeighbours();

            //On parcourt les voisins du sommet actuel pour trouver ses successeurs
            for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
                if(!visited[neighbour.getKey().getLabel()]) {
                    visited[neighbour.getKey().getLabel()] = true;
                    toVisit.add(neighbour.getKey());
                }
            }
        }

        return orderNodes;
    }


    // ------------------------------------------------------------
    // 	  Algorithme de calcul des composantes fortement connexe
    // ------------------------------------------------------------

    /**
     * Algorithme du parcours en profondeur au cas des composantes fortement connexes
     * @param g     graphe à étudier
     * @return      ensemble des noeuds parcourus avec l'ordre de parcours conservé
     */
    public static List<UndirectedNode> parcoursProfondeurCFC(UndirectedGraph g) {
        //Ordre de parcours des noeuds visités
        List<UndirectedNode> visited = new ArrayList<UndirectedNode>();

        //Classification dynamique du parcours du graphe
        //0 = non parcouru, 1 = détection de ses voisins, 2 = exploration compléte
        int[] visiteCFC = new int[g.getNbNodes()];

        debut = new int[g.getNbNodes()];
        fin = new int[g.getNbNodes()];
        compt = 0;

        for(int i = 0; i < g.getNbNodes(); i++) {
            visiteCFC[i] = 0;
            debut[i] = 0;
            fin[i] = 0;
        }


        for(UndirectedNode sommet : g.getNodes()) {
            if(!visited.contains(sommet)) {
                visiterParcoursProfondeurCFC(sommet, visited, visiteCFC, 0);
            }
        }

        //Affichage CFC
        System.out.println("Afficher CFC liste noeuds");
        for(int i = 0; i < g.getNbNodes(); i++) {
            System.out.println("Etat: " + visiteCFC[i] + ", debut: " + debut[i] + ", fin: " + fin[i]);
        }

        return visited;
    }

    /**
     * Algorithme du parcours en profondeur au cas des composantes fortement connexes
     * @param s             sommet visité à cette itération
     * @param visited       ensemble des noeuds parcourus avec l'ordre de parcours conservé
     * @param visiteCFC     Classification dynamique du parcours du graphe
     *                      0 = non parcouru, 1 = détection de ses voisins, 2 = exploration compléte
     * @param profondeur    étage actuel de l'arbre construit à partir du graphe
     */
    private static void visiterParcoursProfondeurCFC(UndirectedNode s, List<UndirectedNode> visited, int[] visiteCFC, int profondeur) {
        System.out.println("Visite CFC Profondeur : " + profondeur + ", noeud : " + s);
        visited.add(s);

        debut[s.getLabel()] = compt;
        compt++;


        visiteCFC[s.getLabel()] = 1;

        /*Map<UndirectedNode, Integer> neighbours = s.getNeighbours();

        for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
            if(!visited.contains(neighbour.getKey()))
                visiterParcoursProfondeur2(neighbour.getKey(), visited);
        }*/

        if( s.getNbNeigh() > 0 ) { // Si il n'y a plus de de fils disponible, Je remonte d'un cran
            Map<UndirectedNode, Integer> neighbours = s.getNeighbours(); // Sinon je récupère la liste des fils

            for(Map.Entry<UndirectedNode, Integer> neighbour : neighbours.entrySet()) {
                if(!visited.contains(neighbour.getKey()))
                    visiterParcoursProfondeurCFC(neighbour.getKey(), visited, visiteCFC, profondeur + 1);
            } // Et on continue d'explorer l'arbre
        }

        visiteCFC[s.getLabel()] = 2;
        fin[s.getLabel()] = compt;
        compt++;

    }



    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(9, 20, false, false, true, 100001);
        GraphTools.afficherMatrix(Matrix);
        UndirectedGraph al = new UndirectedGraph(Matrix);
        System.out.println(al);

        //Question 20
        System.out.println("Question 20 - Affichage parcoursEnProfondeur");
        System.out.println(parcoursProfondeur2(al));

        //Question 21
        System.out.println("Question 21 - Affichage parcoursEnLargeur");
        System.out.println(parcoursLargeur(al, al.getNodes().get(0)));

        //Question 22 Composantes Fortement connexes
        System.out.println("Question 22 - Affichage CFC");
        System.out.println(parcoursProfondeurCFC(al));
    }
}
