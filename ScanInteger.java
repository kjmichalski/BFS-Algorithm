import java.util.Scanner;

public class ScanInteger implements ScanItem<Integer> {
    private Scanner scnr;
    public ScanInteger(Scanner scnr) { this.scnr = scnr; }
    public boolean hasNext() { return scnr.hasNextInt(); }
    public Integer next() { return scnr.nextInt(); }
    public Scanner scanner() { return scnr; }
}
