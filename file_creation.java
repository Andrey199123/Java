//Andrey Vasilyev June 30th, 2023
import java.io.*;
import java.nio.file.*;
public class file_creation {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
        for (int i = 0; i <= 1000; i++) {
            File file = new File("/Users/yuryvasiliev/Downloads/hello" + i + ".png");
            file.createNewFile();
        }
        for (int i=0; i <= 1000; i++) {
            Files.delete(Paths.get("/Users/yuryvasiliev/Downloads/hello" + i + ".png"));
        }
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Completed in " + elapsedTime/1000000 + " milliseconds.");
    }
}
//1934, 176, 174, 233 for txt
//403, 231, 210, 201 for jpg
//188, 201, 190, 187 for png