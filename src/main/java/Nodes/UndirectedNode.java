package Nodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by gsimonin on 05/01/2020.
 */
public class UndirectedNode extends AbstractNode {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    private Map<UndirectedNode, Integer> neighbours;

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedNode(int i) {
        super(i);
        this.neighbours = new LinkedHashMap<>();
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * @return the list of neighbors of the current node this
     */
    public Map<UndirectedNode, Integer> getNeighbours() {
        return neighbours;
    }

    /**
     * @param neigh the new list of neighbors for node this
     */
    public void setNeighbours(Map<UndirectedNode, Integer> neigh) {
		this.neighbours = neigh;
	}

    /**
     * @return the number of neighbors of node this
     */
	public int getNbNeigh() {
        return neighbours.size();
    }
	
	/**
	 * add a new neighbour with its value cost. If the neighbour exists, the weight is changed.
	 */
	public void addNeigh(UndirectedNode v, int val) {
		this.neighbours.put(v, val);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UndirectedNode that = (UndirectedNode) o;
        return Objects.equals(super.getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getLabel());
    }
}
