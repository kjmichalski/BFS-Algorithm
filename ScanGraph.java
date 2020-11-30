import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScanGraph<E> implements ScanItem<Graph<E>> {
    private Scanner scnr;
    private ScanItem<E> si;
    private ScanItem<GraphNode<E>> nsi;
    private ScanItem<GraphEdge<E>> esi;
    private Pattern ghdr, endgptn;
    private Pattern comment;
    public ScanGraph(ScanItem<E> si) {
        this.si = si;
        this.nsi = new ScanGNode<E>(si);
        this.esi = null;
        this.scnr = nsi.scanner();
        ghdr = Pattern.compile("Graph:");
        endgptn = Pattern.compile("endGraph");
        comment = Pattern.compile("//"); // Java style comments "//...<end-of-line>"
    }
    public boolean hasNext() {
        return scnr.hasNext(ghdr);
    }
    public Graph<E> next() {
        Graph<E> g = new Graph<>();
        // read in header
        if ( ! scnr.hasNext(ghdr) )
            throw(new NoSuchElementException(this.getClass().getSimpleName()+" header"));
        scnr.next(ghdr);
        // read in nodes
        while ( nsi.hasNext() ) {
            GraphNode<E> n = nsi.next();
            g.addNode(n.element());
        }
        // read in edges
        esi = new ScanGEdge<E>(si,g);
        while ( esi.hasNext() ) {
            GraphEdge<E> e = esi.next();
            g.addEdge(e.from().element(), e.to().element());
        }
        // read end-of-graph
        if ( scnr.hasNext(endgptn) )
            scnr.next(endgptn);
        return g;
    }
    public Scanner scanner() {
        return scnr;
    }
    public void skipComment() {
        while ( scnr.hasNext(comment) )
            scnr.nextLine();
    }

    public static void main(String[] args) {
        String fname = "resources\\ScanGraph-test.txt";
        Scanner scnr = null;
        try {
            scnr = new Scanner(new File(fname));
        }
        catch (Exception e) {
            System.out.println("Cannot open file "+fname);
            System.exit(1);
        }
        ScanGraph<String> sgi = new ScanGraph<String>(new ScanString(scnr));
        Graph<String> g = null;
        sgi.skipComment();
        if ( sgi.hasNext() )
            g = sgi.next();
        System.out.println(g);
        System.out.println();

        System.out.println("Now a graph with integer nodes: ");
        ScanGraph<Integer> sigi = new ScanGraph<Integer>(new ScanInteger(scnr));
        sigi.skipComment();
        if ( sigi.hasNext() ) {
            try {
                Graph<Integer> ig = sigi.next();
                System.out.println(ig);
            }
            catch(Exception e) {
                System.out.println("Error: "+e);
                System.out.println("Context: "+
                        sigi.scanner().nextLine());
                System.exit(1);
            }

        }
    }
}
