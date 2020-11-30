import java.io.File;
import java.util.*;

public class Graph<E> {
  private HashMap<E,GraphNode<E>> nodeMap;

  public Graph() {
    nodeMap = new HashMap<E,GraphNode<E>>();
  }

  public GraphNode<E> getNode(E elt) throws NoSuchElementException {
    if ( nodeMap.containsKey(elt) )
      return nodeMap.get(elt);
    else
      throw(new NoSuchElementException("getNode: "+elt));
  }

  public GraphNode<E> addNode(E elt) {
    if ( ! nodeMap.containsKey(elt) )
      return nodeMap.put(elt,new GraphNode<E>(elt));
    else  // throw exception??
      return nodeMap.get(elt); // not actually adding a node
  }

  public GraphEdge<E> addEdge(E eltfrom, E eltto)
          throws NoSuchElementException {
    if ( nodeMap.containsKey(eltfrom) && nodeMap.containsKey(eltto) ) {
      GraphNode<E> nfrom = getNode(eltfrom);
      GraphNode<E> nto   = getNode(eltto);
      nfrom.addEdge(nto);
      return new GraphEdge<E>(nfrom,nto);
    }
    else
      return null;
  }

  public void removeEdge(E eltfrom, E eltto) {
    if ( nodeMap.containsKey(eltfrom) && nodeMap.containsKey(eltto) ) {
      GraphNode<E> nfrom = getNode(eltfrom);
      GraphNode<E> nto   = getNode(eltto);
      if ( nfrom.hasNeighbor(nto) ) {
        nfrom.removeEdge(nto);
      }
    }
  }

  public void makeUndirected() {
    Iterator<GraphEdge<E>> edgeIter = edges();
    while ( edgeIter.hasNext() ) {
      GraphEdge<E> e = edgeIter.next();
      GraphNode<E> from = e.from(), to = e.to();
      if ( ! to.hasNeighbor(from) )
        to.addEdge(from);
    }
  }

  public boolean isUndirected() {
    Iterator<GraphEdge<E>> edgeIter = edges();
    while ( edgeIter.hasNext() ) {
      GraphEdge<E> e = edgeIter.next();
      GraphNode<E> from = e.from(), to = e.to();
      if ( ! to.hasNeighbor(from) )
        return false;
    }
    return true;
  }



  /*
   * @TODO: complete bfs(E) method
   */
  /* bfs returns a list of graph nodes in order of visits */
  public List<GraphNode<E>> bfs(E start) {
    GraphNode<E> startNode = getNode(start);
    Queue<GraphNode<E>> q = new ArrayDeque<GraphNode<E>>();
    ArrayList<GraphNode<E>> list = new ArrayList<GraphNode<E>>();
    HashSet<GraphNode<E>> visited = new HashSet<GraphNode<E>>();
    list.add(startNode);
    visited.add(startNode);
    q.add(startNode);
    /* while loop for going over q, removing one item each time * /
    /* use node iterator for nodes connected to node removed from q */
    while ( ! q.isEmpty() ) {
      // code here ( complete the next 2 lines)
      GraphNode<E> from = q.remove();// @TODO: code;
      Iterator<GraphNode<E>> nbrIter = from.neighbors();  // @TODO: code;
      /* nested while loop goes of  over neighbors */
      while ( nbrIter.hasNext() ) {
        GraphNode<E> to = nbrIter.next(); // @TODO: code;
        /* if statement unvisited neighbors (if not visited add to list, visited and q) */
        if ( ! visited.contains(to) ) {
          visited.add(to);
          q.add(to);
          list.add(to);
          // @TODO: code here (3 lines)
        }
      }
    }
    return list;
  }


  /*
   * @TODO: complete bfsPrev(E) method
   */
  /* returns the hashmap (prev) for the bfs storing the (to, from) graphNodes info */
  public HashMap<GraphNode<E>,GraphNode<E>> bfsPrev(E start) {
    GraphNode<E> startNode = getNode(start);
    Queue<GraphNode<E>> q = new ArrayDeque<GraphNode<E>>();
    HashMap<GraphNode<E>,GraphNode<E>> prev = new HashMap<GraphNode<E>,GraphNode<E>>();
    prev.put(startNode,startNode); // root of BFS tree
    q.add(startNode);
    /* while loop for going over q, removing one item each time * /
    /* use node iterator for nodes connected to node removed from q */
    while ( ! q.isEmpty() ) {
      //code here ( complete the next 2 lines)
      GraphNode<E> from = q.remove(); // @TODO: code;
      Iterator<GraphNode<E>> nbrIter = from.neighbors(); // @TODO: code;
      /* nested while loop goes of  over neighbors */
      while (  nbrIter.hasNext() ) {
        GraphNode<E> to = nbrIter.next();// @TODO: code;
        if ( ! prev.containsKey(to) )
        {
            q.add(to);
            prev.put(to, from);
          // @TODO: code (2 lines)
          //System.out.print("hello");
        } // if ...
      } // while ...
    } // while ...
    return prev;
  }





  public Collection<GraphNode<E>> nodes() { return nodeMap.values(); }

  public Iterator<GraphEdge<E>> edges() {
    Iterator<GraphEdge<E>> it = new Iterator<GraphEdge<E>>() {
      private Iterator<GraphNode<E>> fromIter = null;
      private Iterator<GraphNode<E>> toIter = null;
      private GraphNode<E> from = null, to = null;
      private int state = 0; // start state, end state is 4
      private final int open = 1, closed = 2;
      private int lock = open;

      private void step() { // sets from and to fields
        boolean breakLoop = false;
        while ( ! breakLoop ) {
          switch ( state ) {
            case 0:
              fromIter = nodeMap.values().iterator();
              if ( fromIter.hasNext() ) {
                from = fromIter.next();
                toIter = from.neighbors();
                state = 1;
              } else {
                state = 4;
                breakLoop = true;
              }
              break;
            case 1:
              if ( toIter.hasNext() ) {
                to = toIter.next();
                state = 2;
                breakLoop = true;
              } else {
                state = 3;
              }
              break;
            case 2:
              if ( toIter.hasNext() ) {
                to = toIter.next();
                state = 2;
                breakLoop = true;
              } else {
                state = 3;
              }
              break;
            case 3:
              if ( fromIter.hasNext() ) {
                from = fromIter.next();
                toIter = from.neighbors();
                state = 1;
              } else {
                state = 4;
                breakLoop = true;
              }
              break;
            default: // include case 4: (== end state)
              breakLoop = true;
              break;
          }
        }
      }

      @Override
      public boolean hasNext() {
        if ( lock == open )
          step();
        lock = closed;
        return state != 4;
      }

      @Override
      public GraphEdge<E> next() {
        if ( lock == open )
          step();
        lock = open;
        return new GraphEdge<E>(from,to);
      }
    };
    return it;
  }

  public String toString() {
    String s = "Graph: \n";
    for ( GraphNode<E> n : nodes() )
      s += n.toString()+"\n";

    Iterator<GraphEdge<E>> iter = edges();
    while ( iter.hasNext() ) {
      GraphEdge<E> e = iter.next();
      s += e.toString()+"\n";
    }
    s += "endGraph\n";
    return s;
  }

  public static void main(String[] args) {

    Graph<Integer> g1 = null;
    //String fname = "resources\\GraphTest-Integer.txt";
    String fname = "resources/GraphTest-Integer.txt"; // Linux/Mac users may need to use this path for fname instead
    Scanner scnr = null;
    try {
      scnr = new Scanner(new File(fname));
    }
    catch (Exception e) {
      System.out.println("Cannot open file "+fname);
      System.exit(1);
    }
    ScanGraph<Integer> sg = new ScanGraph<Integer>(new ScanInteger(scnr));
    sg.skipComment();
    if ( sg.hasNext() )
      g1 = sg.next();

    System.out.println();
    System.out.println("Is g1 undirected? " + g1.isUndirected());

    System.out.println(g1);
    System.out.println();
    System.out.println("Is g undirected? " + g1.isUndirected());
    System.out.println();
    g1.makeUndirected();
    System.out.println(g1);
    System.out.println();
    System.out.println("Is g undirected now? " + g1.isUndirected());
    System.out.println();
    System.out.println("BFS list = "+ g1.bfs(2));
    System.out.println();
    System.out.println("BFS previous map = "+ g1.bfsPrev(2));
    System.out.println();

    System.out.println("==============================================");
    System.out.println();
  }
}
