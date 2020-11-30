import java.util.Scanner;

public interface ScanItem<E> {
    public boolean hasNext();
    public E next();
    public Scanner scanner();
    default public void skipComment() { };
}
