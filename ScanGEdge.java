import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScanGEdge<E> implements ScanItem<GraphEdge<E>> {
    private Scanner scnr;
    private ScanItem<E> si;
    private Graph<E> g;
    private Pattern ehdr, arrow;
    public ScanGEdge(ScanItem<E> si, Graph<E> g) {
        this.scnr = si.scanner();
        this.si = si;
        this.g = g;
        ehdr  = Pattern.compile("Edge:");
        arrow = Pattern.compile("->");
    }
    public boolean hasNext() {
        return scnr.hasNext(ehdr);
    }
    public GraphEdge<E> next() {
        E e1, e2;
        if ( ! scnr.hasNext(ehdr) ) // "Edge:"
            throw(new NoSuchElementException(this.getClass().getSimpleName()));
        scnr.next(ehdr);
        if ( ! si.hasNext() )
            throw(new NoSuchElementException(this.getClass().getSimpleName()));
        e1 = si.next();
        if ( ! scnr.hasNext(arrow) )
            throw(new NoSuchElementException(this.getClass().getSimpleName()));
        scnr.next(arrow);
        if ( ! si.hasNext() )
            throw(new NoSuchElementException(this.getClass().getSimpleName()));
        e2 = si.next();
        GraphNode<E> n1, n2;
        n1 = g.getNode(e1);
        n2 = g.getNode(e2);
        return new GraphEdge<>(n1,n2);
    }
    public Scanner scanner() { return scnr; }
}
