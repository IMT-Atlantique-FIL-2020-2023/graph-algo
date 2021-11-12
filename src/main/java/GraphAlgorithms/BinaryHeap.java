package GraphAlgorithms;


import java.util.ArrayList;
import java.util.List;

public class BinaryHeap {

    private List<Integer> nodes;

    public BinaryHeap() {
        this.nodes = new ArrayList<Integer>();

    }

/*    public void resize() {
        int[] tab = new int[this.nodes.size + 32];
        for (int i = 0; i < nodes.size; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }*/

    public boolean isEmpty() {
        return nodes.size() == 0;
    }

    //On ne swap pas si la valeur du parent = element
    public void insert(int element) {
    	this.nodes.add(element);

        int posElement = nodes.lastIndexOf(element);
    	int posFather = (posElement - 1)/2;

    	while(nodes.get(posElement) < nodes.get(posFather)) {
    	    swap(posFather, posElement);

    	    posElement = posFather;
    	    posFather = (posElement - 1)/2;
        }
    }

    public int remove() {
        if(nodes.size() <= 0) {
            return -1;
        } else if(nodes.size() == 1) {
            return nodes.remove(0);
        } else {
            //Echanger la racine avec la dernière feuille
            swap(0, nodes.size()-1);

            //replacer la nouvelle racine
            int valRoot = nodes.remove(nodes.size()-1);
            int posElement = 0; //position de l'élément à replacer
            int dest = getBestChildPos(posElement);

            System.out.println("Remove() ");
            while(dest != -1 && dest < nodes.size() && nodes.get(posElement) >= nodes.get(dest)) {
                swap(posElement, dest);
                posElement = dest;
                dest = getBestChildPos(posElement);
            }

            return valRoot;
        }
    }

    /**
     * Retourne l'indice du meilleur des deux fils comparés entre eux (dans le cas ou fils2 existe)
     * @param src
     * @return
     */
    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return -1;
        } else {
            if(2*src+2 >= nodes.size()) {
                return 2*src+1;
            } else {
                if(nodes.get(2*src+1) <= nodes.get(2*src+2)) {
                    return 2*src+1;
                } else {
                    return 2*src+2;
                }

            }
        }
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 *
     * Rq:Pas besoin de tester le second fils, si le 1er est une feuille, alors le second l'est
     *
	 * @returns true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
    	return 2*src+1 >= nodes.size();
    }

    private void swap(int father, int child) {
        int temp = nodes.get(father);
        nodes.set(father, nodes.get(child));
        nodes.set(child, temp);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            s.append(nodes.get(i)).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= nodes.size()) {
                return nodes.get(left) >= nodes.get(root) && testRec(left);
            } else {
                return nodes.get(left) >= nodes.get(root) && testRec(left) && nodes.get(right) >= nodes.get(root) && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand);
            jarjarBin.insert(rand);
            k--;
        }
     // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());

        jarjarBin.remove();
        System.out.println("\n avec 1 removes " + jarjarBin);
        System.out.println(jarjarBin.test());
        jarjarBin.remove();
        System.out.println("\n avec 2 removes " + jarjarBin);
        System.out.println(jarjarBin.test());
    }

}
