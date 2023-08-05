//Andrey Vasilyev, June 30th, 2023
package Class_File;
import java.io.*;
import java.nio.file.*;
import java.util.stream.IntStream;
public class file_creation {
    public static void main(String[] args) {
        final long startTime = System.nanoTime();
        IntStream.rangeClosed(0, 1000).forEach(i -> {
            try {
                new File("/Users/yuriyvasiliev/Downloads/hello" + i + ".txt").createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        IntStream.rangeClosed(0, 1000).forEach(i -> {
            try {
                Files.delete(Paths.get("/Users/yuriyvasiliev/Downloads/hello" + i + ".txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Completed in " + (System.nanoTime() - startTime)/1000000 + " milliseconds.");
    }
}
//534, 622, 539 for txt
//729, 519, 455, 523 for jpg
//764, 505, 569, 468 for png
