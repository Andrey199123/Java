import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;
public class ScanComputer extends SimpleFileVisitor<Path> {
    long fileCounter = 0;
    long size = 0;
    long years = 0;
    int failedCounter = 0;
    public ScanComputer() {
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        fileCounter++;
        size += attr.size();
        years += Integer.parseInt(attr.creationTime().toString().substring(0, 4));
        return CONTINUE;
    }


    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        failedCounter++;
        return CONTINUE;
    }
}