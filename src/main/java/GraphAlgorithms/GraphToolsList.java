package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	/**
	 * Applique l'algorithme de Belmann sur un graphe
	 *
	 * @param g graphe à étudier
	 * @param s indice du sommet de départ
	 * @return la dernière étape du tableau. -1 si cycle négatif
	 *
	 * RQ : ajouter un try/Catch dans l'appel à la fonction
	 */
	public int[] belmann(DirectedValuedGraph g, DirectedNode s) throws Exception {
		int[][] dist = new int[g.getNbNodes() +1][g.getNbNodes()];
		//nbNodes + 1 pour le nombre d'itérations pour le cas d'initialisation et le cas de vérification

		//Initialisation
		for(int i = 0; i < g.getNbNodes(); i++) {
			dist[0][i] = Integer.MAX_VALUE;
		}
		dist[0][s.getLabel()] = 0;

		//Parcours
		//K = numéro itération
		//v = chaque sommet de V, G=(V,E)
		//x = liste des prédecesseurs
		for(int k = 1; k < g.getNbNodes(); k++) {
			for(DirectedNode v : g.getNodes()) {
				for (Map.Entry x : v.getPreds().entrySet()) {
					dist[k][v.getLabel()] = Integer.min(dist[k-1][v.getLabel()], dist[k-1][((DirectedNode) x.getKey()).getLabel()]);
				}
			}
		}

		//Test cycles négatifs. Si un changement apparaît, alors il y a un cycle négatif
		for(DirectedNode v : g.getNodes()) {
			for (Map.Entry x : v.getPreds().entrySet()) {
				dist[g.getNbNodes()][v.getLabel()] = Integer.min(dist[g.getNbNodes() - 1][v.getLabel()], dist[g.getNbNodes() - 1][((DirectedNode) x.getKey()).getLabel()]);
				if(dist[g.getNbNodes()][v.getLabel()] != dist[g.getNbNodes() - 1][v.getLabel()])
					throw new Exception("Belmann, cycle négatif présent dans G");
			}
		}


		return dist[g.getNbNodes()];
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph al = new DirectedGraph(Matrix);
		System.out.println(al);

		// A completer
	}
}
