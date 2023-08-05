import java.nio.file.FileStore;
import java.nio.file.FileSystems;

public class Main {
    public static void main(String[] args) {
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            System.out.println(store);
        }
    }
}
