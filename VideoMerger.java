//Andrey Vasilyev July 11th, 2023
//Make sure to add the video on line 17, the folder path on line 18, and where the image will be saved on line 93 and/or 118

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
public class VideoMerger {
    public static void main(String[] args) throws IOException, InterruptedException {
        String videoPath = "/Users/yuryvasiliev/IdeaProjects/TimedTests/src/video.mp4"; // Path to the input video file
        String folderPath = "/Users/yuryvasiliev/IdeaProjects/TimedTests/src/output"; // Path to the output folder to store frames
        File outputDir = new File(folderPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        // Execute FFmpeg command to extract frames
        //USE /opt/ffmpeg INSTEAD OF FFMPEG BECAUSE IT'S NOT ADDED TO PATH
        ProcessBuilder processBuilder = new ProcessBuilder("/opt/ffmpeg", "-i", videoPath, folderPath + "/frame_%d.jpg");
        Process process = processBuilder.start();
        process.waitFor();
        checkInput(getImageDimension(new File(folderPath + "/frame_1.jpg")), folderPath);
        deleteDirectory(outputDir);
    }
    /*
     * Gets image dimensions for given file
     * @param imgFile image file
     * @return dimensions of image
     * @throws IOException if the file is not a known image
     */
    public static Dimension getImageDimension(File imgFile) throws IOException {
        int pos = imgFile.getName().lastIndexOf(".");
        if (pos == -1)
            throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
        String suffix = imgFile.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while(iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                return new Dimension(width, height);
            } catch (IOException e) {
                System.out.println("Error reading: " + imgFile.getAbsolutePath());
            } finally {
                reader.dispose();
            }
        }
        throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
    }
    //Method exists for recursion if the input was not understood
    public static void checkInput(Dimension aspectRatio, String folderPath) throws IOException {
        String[] fileList = new File(folderPath).list();
        Arrays.sort(fileList);
        int numberOfFiles = fileList.length;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want the strips in columns or rows?");
        String columnsOrRows = scanner.nextLine();
        long startTime = System.nanoTime();
        int width = (int) aspectRatio.getWidth();
        int height = (int) aspectRatio.getHeight();
        int thickness;
        Graphics2D g2d;
        switch (columnsOrRows) {
            case "columns" -> {
                if (numberOfFiles > width) {
                    System.out.println("The video has too many frames for its width. Not all frames will be used.");
                    fileList = keepEveryNthValue(fileList, (int) ceil((double) numberOfFiles /width));
                    numberOfFiles = fileList.length;
                }
                thickness = (int) floor((double) width / numberOfFiles);
                int counter = 0;
                BufferedImage sliceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                // Get the Graphics2D object to draw on the sliceImage
                g2d = sliceImage.createGraphics();
                for(String i : fileList) {
                    BufferedImage originalImage = ImageIO.read(new File(folderPath + "/" + i));
                    if (!(counter == numberOfFiles - 1)) {
                        // Draw the slice from the original image onto the sliceImage
                        g2d.drawImage(originalImage, thickness * counter, 0, thickness * (counter + 1), height, thickness * counter, 0, thickness * (counter + 1), height, null);
                        counter++;
                    } else {
                        g2d.drawImage(originalImage, counter*thickness, 0, width, height, counter * thickness, 0, width, height, null);
                    }
                }
                ImageIO.write(sliceImage, "jpg", new File("/Users/yuryvasiliev/IdeaProjects/TimedTests/src/VideoMerged.jpg"));
                System.out.printf("Completed in %.2f seconds.", (double) (System.nanoTime() - startTime) / 1000000000);
                //6.66 seconds
            }
            case "rows" -> {
                if (numberOfFiles > height) {
                    System.out.println("The video has too many frames for its height. Not all frames will be used.");
                    fileList = keepEveryNthValue(fileList, (int) ceil((double) numberOfFiles /height));
                    numberOfFiles = fileList.length;
                }
                //Simply replace the order of parameters for the strips to be in rows
                thickness = (int) floor((double) height / numberOfFiles);
                int counter = 0;
                BufferedImage sliceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                // Get the Graphics2D object to draw on the sliceImage
                g2d = sliceImage.createGraphics();
                for(String i : fileList) {
                    BufferedImage originalImage = ImageIO.read(new File(folderPath + "/" + i));
                    if (!(counter == numberOfFiles - 1)) {
                        // Draw the slice from the original image onto the sliceImage
                        g2d.drawImage(originalImage, 0, thickness * counter, width, thickness * (counter + 1), 0, thickness * counter, width, thickness * (counter + 1), null);
                        counter++;
                    } else {
                        g2d.drawImage(originalImage, 0, thickness * counter, width, height, 0, thickness * counter, width, height, null);
                    }
                }
                ImageIO.write(sliceImage, "jpg", new File("/Users/yuryvasiliev/IdeaProjects/TimedTests/src/VideoMerged2.jpg"));
                System.out.printf("Completed in %.2f seconds.", (double) (System.nanoTime() - startTime) / 1000000000);
                //3.47 seconds
            }
            default -> {
                System.out.println("Input was not understood.");
                checkInput(aspectRatio, folderPath);
            }
        }
    }
    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Recursively delete subdirectories
                    } else {
                        file.delete(); // Delete files
                    }
                }
            }
            directory.delete(); // Delete the empty directory
        }
    }
    public static String[] keepEveryNthValue(String[] originalArray, int n) {
        if (n <= 0 || originalArray == null || originalArray.length == 0) {
            return new String[0]; // Return an empty array if n is invalid or the original array is empty
        }

        int newSize = (originalArray.length + n - 1) / n; // Calculate the new array size
        String[] result = new String[newSize];

        for (int i = 0, j = 0; i < originalArray.length; i += n, j++) {
            result[j] = originalArray[i]; // Copy every nth value to the new array
        }

        return result;
    }
}