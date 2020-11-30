import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScanGNode<E> implements ScanItem<GraphNode<E>> {
    private Scanner scnr;
    private ScanItem<E> si;
    private Pattern nhdr;
    public ScanGNode(ScanItem<E> si) {
        this.si = si;
        this.scnr = si.scanner();
        nhdr = Pattern.compile("Node:");
    }
    public boolean hasNext() {
        return scnr.hasNext(nhdr);
    }
    public GraphNode<E> next() {
        scnr.next(nhdr);
        if ( si.hasNext() )
            return new GraphNode<>(si.next());
        else
            throw(new NoSuchElementException(this.getClass().getSimpleName()+" value"));
        // return null; // should not happen
    }
    public Scanner scanner() { return scnr; }

    public static void main(String[] args) {
        Scanner scnr = null;
        try {
            scnr = new Scanner(
                    new File("resources\\ScanGNode-test.txt"));
        }
        catch (Exception e) {
            System.out.println("Cannot open file"+e);
            System.exit(1);
        }
        ScanItem<String> ss = new ScanString(scnr);
        ScanItem<GraphNode<String>> sn = new ScanGNode<>(ss);
        while ( sn.hasNext() )
            System.out.println(sn.next());
        System.out.println("What's next is \""+scnr.next()+"\"");
    }
}
