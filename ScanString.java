import java.util.Scanner;

public class ScanString implements ScanItem<String> {
    private Scanner scnr;
    public ScanString(Scanner scnr) {
        this.scnr = scnr;
    }
    @Override
    public boolean hasNext() { return scnr.hasNext(); }
    @Override
    public String next() { return scnr.next(); }
    @Override
    public Scanner scanner() { return scnr; }
}
