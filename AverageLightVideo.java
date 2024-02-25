//Andrey Vasilyev July 13th, 2023
//Make sure to add the path to the video on line 171
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static java.lang.Math.floor;
public class AverageLightVideo extends JFrame {
    AverageLightVideo(List<List<Integer>> averageLightValues) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        Border border = BorderFactory.createLineBorder(Color.white, 10);
        JPanel leftPanel;
        if (averageLightValues.size() < 900) {
            leftPanel = new JPanel(new GridLayout((int) Math.sqrt(averageLightValues.size()), (int) Math.sqrt(averageLightValues.size()), 10, 10));
            leftPanel.setPreferredSize(new Dimension(500, 500));
            for (int i = 0; i < Math.pow((int) Math.sqrt(averageLightValues.size()), 2); i++) {
                JPanel square = new JPanel();
                square.setBackground(new Color(averageLightValues.get(i).get(0), averageLightValues.get(i).get(1), averageLightValues.get(i).get(2))); // Set background color based on RGB values
                leftPanel.add(square);
            }
        } else {
            leftPanel = new JPanel(new GridLayout(30, 30, 10, 10));
            leftPanel.setPreferredSize(new Dimension(500, 500));
            //Gets every nth frame's average color to be shown
            for (int i = 0; i < 900; i++) {
                JPanel square = new JPanel();
                square.setBackground(new Color(averageLightValues.get((int) (i * floor((double) averageLightValues.size() /900))).get(0), averageLightValues.get((int) (i * floor((double) averageLightValues.size() /900))).get(1), averageLightValues.get((int) (i * floor((double) averageLightValues.size() /900))).get(2)));
                leftPanel.add(square);
            }
        }
        leftPanel.setBorder(border);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(calculateAverageRGB(averageLightValues).get(0), calculateAverageRGB(averageLightValues).get(1), calculateAverageRGB(averageLightValues).get(2)));
        rightPanel.setPreferredSize(new Dimension(500, 500));
        rightPanel.setBorder(border);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    public static List<Integer> getAverageLightValue(String imagePath) {
        try {
            // Load the image
            BufferedImage image = ImageIO.read(new File(imagePath));

            // Get image dimensions
            int width = image.getWidth();
            int height = image.getHeight();

            // Variables to store the sum of light values
            long sumRed = 0;
            long sumGreen = 0;
            long sumBlue = 0;

            // Iterate through each pixel and accumulate light values
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    sumRed += red;
                    sumGreen += green;
                    sumBlue += blue;
                }
            }

            // Calculate the average light values
            int totalPixels = width * height;
            int avgRed = (int) (sumRed / totalPixels);
            int avgGreen = (int) (sumGreen / totalPixels);
            int avgBlue = (int) (sumBlue / totalPixels);

            // Create a list and add the average light values
            List<Integer> averageLightValues = new ArrayList<>();
            averageLightValues.add(avgRed);
            averageLightValues.add(avgGreen);
            averageLightValues.add(avgBlue);

            return averageLightValues;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void deleteDirectory(File directory) {
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
    public static List<Integer> calculateAverageRGB(List<List<Integer>> rgbValues) {
        if (rgbValues == null || rgbValues.isEmpty()) {
            throw new IllegalArgumentException("RGB values list is null or empty.");
        }

        int totalPixels = 0;
        long sumRed = 0;
        long sumGreen = 0;
        long sumBlue = 0;

        for (List<Integer> rgb : rgbValues) {
            sumRed += rgb.get(0);
            sumGreen += rgb.get(1);
            sumBlue += rgb.get(2);

            totalPixels++;
            }

        int avgRed = (int) sumRed / totalPixels;
        int avgGreen = (int) sumGreen / totalPixels;
        int avgBlue = (int) sumBlue / totalPixels;
        List<Integer> averageLightValue = new ArrayList<>();
        averageLightValue.add(avgRed);
        averageLightValue.add(avgGreen);
        averageLightValue.add(avgBlue);
        return averageLightValue;
    }
    //To determine how many pixels are measured
    private static Dimension getImageDimension(File imgFile) throws IOException {
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
    public static void main (String[]args) throws IOException, InterruptedException {
        final long startTime = System.nanoTime();
        final String videoPath = "/Users/yuryvasiliev/IdeaProjects/TimedTests/src/Passport to Paradise.mov";
        final String folderPath = "/Users/yuryvasiliev/IdeaProjects/TimedTests/src/output";
        final File outputDir = new File(folderPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        ProcessBuilder processBuilder = new ProcessBuilder("/opt/ffmpeg", "-i", videoPath, folderPath + "/frame_%d.jpg");
        Process process = processBuilder.start();
        process.waitFor();
        String[] fileList = new File(folderPath).list();
        assert fileList != null;
        Arrays.sort(fileList);
        List<List<Integer>> averageLightValues = new ArrayList<>();
        for(String i : fileList) {
            averageLightValues.add(getAverageLightValue(folderPath + "/" + i));
        }
        System.out.println("Average Light Values: " + calculateAverageRGB(averageLightValues));
        System.out.printf("%,d pixels measured.%n", (long) (getImageDimension(new File(folderPath + "/frame_1.jpg")).getWidth() * getImageDimension(new File(folderPath + "/frame_1.jpg")).getHeight() * averageLightValues.size()));
        deleteDirectory(outputDir);
        System.out.printf("Completed in %.2f seconds.", (double) (System.nanoTime() - startTime) / 1000000000);
        new AverageLightVideo(averageLightValues);
        //203.09 seconds, 4,228,070,400 pixels
    }
}