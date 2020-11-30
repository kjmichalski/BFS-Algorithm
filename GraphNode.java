import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Iterable;
import java.util.Scanner;
import java.util.regex.Pattern;

/** GraphNode<E> is a node type for the Graph;
    E is the node element type
 */
public class GraphNode<E> {
  private List<GraphNode<E>> nbrs; // nbrs is list of neighbors of this node
  private E elt; // elt is element stored in this node
  public GraphNode(E e, List<GraphNode<E>> nb)
  { elt = e; nbrs = nb; }
  public GraphNode(E e)
  { elt = e; nbrs = new ArrayList<GraphNode<E>>(); }
  public E element() { return elt; }
  public Iterator<GraphNode<E>> neighbors()   { return nbrs.iterator(); }
  public boolean hasNeighbor(GraphNode<E> to) { return nbrs.contains(to); }
  public void    addEdge   (GraphNode<E> to)  { nbrs.add(to); }
  public void    removeEdge(GraphNode<E> to)  { nbrs.remove(to); }
  public String toString() {
    String s = "Node: " + elt; // + " [";
    return s;
  }

  @Override
  public boolean equals(Object n) {
    GraphNode<E> node = (GraphNode) n;
    return this.elt.equals(node.elt);
  }
}
