import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystemException;

public class ScanComputerMain {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        Path startingDir = Path.of("/");
        ScanComputer fvr = new ScanComputer();
        try {
            Files.walkFileTree(startingDir, fvr);
        } catch(FileSystemException e) {
            System.err.println(e);
        }
        System.out.printf("%,d files \n", fvr.fileCounter);
        System.out.println("Average file size in kilobytes: " + (fvr.size/fvr.fileCounter)/1000);
        System.out.println("Average file year of files: " + fvr.years/fvr.fileCounter);
        System.out.println("Inaccessible directory count: " + fvr.failedCounter);
        System.out.printf("Completed in %.2f seconds.", (double) (System.nanoTime() - startTime) / 1000000000);
        /*
        Users
        250,057 files
        Average file size in bytes: 282504
        Average file year: 2021
        32 inaccessible directories
        276.00 Seconds

        Root
        1,308,016 files
        Average file size in kilobytes: 49
        Average file year: 2018
        228 inaccessible directories
        752.11 seconds
         */
    }
}

