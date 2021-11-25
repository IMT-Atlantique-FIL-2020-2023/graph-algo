package GraphAlgorithms;

import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.UndirectedNode;

import java.util.*;

public class TP4PrimBinaryHeap {

    // ------------------------------------------
    // 	  Algorithme de PRIM avec tabinaire
    // ------------------------------------------


    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateValuedGraphData(30, false, false, true, false, 100008);
        GraphTools.afficherMatrix(Matrix);
        UndirectedValuedGraph alUndirected = new UndirectedValuedGraph(Matrix);
        System.out.println(alUndirected);


        //Question 5
        Tuple<Set<Triple<UndirectedNode, UndirectedNode, Integer>>, Integer> res = primBinaryHeapEdge(alUndirected, alUndirected.getNodes().get(0));
        System.out.printf("Algorithme de PRIM s=%d: poids minimal = %d et liste des arêtes de l'arbre recouvrant créé : %s\n", 0, res.getValue(), res.getKey());
    }

    /**
     * TP4 Q6
     * Comment utiliseriez-vous la structure de tas binaire dans votre mise en œuvre de l’algo-
     * rithme PRIM ? Quel opération faut-il ajouter dans la structure de tas binaire ? Quel gain en terme de
     * complexité par rapport à votre algorithme ?
     *
     * @param g graph
     * @param s initial node
     * @return a tuple with the list of edge in the tree and minimal cost
     */
    public static Tuple<Set<Triple<UndirectedNode, UndirectedNode, Integer>>, Integer> primBinaryHeapEdge(UndirectedGraph g, UndirectedNode s) {


        int cost = 0;
        Set<UndirectedNode> a = new HashSet<>();
        a.add(s);
        BinaryHeapEdge tab = new BinaryHeapEdge();
        Set<Triple<UndirectedNode, UndirectedNode, Integer>> visitedEdges = new HashSet<>();

        for (int i = 1; i < g.getNbNodes(); i++) {
            for (UndirectedNode x : a) {
                for (Map.Entry<UndirectedNode, Integer> y : x.getNeighbours().entrySet()) {
                    Triple<UndirectedNode, UndirectedNode, Integer> edge = new Triple<>(x, y.getKey(), y.getValue());
                    // ajouter dans le tas binaire les arêtes/arcs (x, y) non déjà ajoutées tels que x ∈ A, y 6∈ A
                    if (visitedEdges.contains(edge)) {
                        continue;
                    }
                    tab.insert(x, y.getKey(), y.getValue());
                }
            }
            // - prendre l’élément min du tas (si (x, y) avec x et y ∈ A, prendre suivant)
            Triple<UndirectedNode, UndirectedNode, Integer> min = tab.remove();
            while (min != null && a.contains(min.getFirst()) && a.contains(min.getSecond())) {
                min = tab.remove();
            }
            if (min == null) {
                continue;
            }

            cost += min.getThird();
            // - cost = cost + w(x, y)
            visitedEdges.add(min);
            // - A = A ∪ {y}
            a.add(min.getSecond());
        }
        return new Tuple<>(visitedEdges, cost);
        // Complexité : O((n + m) ∗ log(n)) = O(m ∗ log(n))
    }
}

class Tuple<K, V> extends AbstractMap.SimpleEntry<K, V> {
    public Tuple(K key, V value) {
        super(key, value);
    }
}
