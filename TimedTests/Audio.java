//Andrey Vasilyev July 5th, 2023
//Finds the maximum number of audio tracks that can be run on a computer using binary sort algorithm
//Make sure to specify bounds on line 16 and 17 and the file path to the track on line 32
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.Math.round;
public class Audio {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException {
        long startTime = System.nanoTime();
        int[] bounds = new int[]{0, 50};
        //originalBounds are to see if the lower or upper bounds is too small or large
        int[] originalBounds = new int[]{0, 50};
        System.out.println(loop(bounds, originalBounds, 0));
        double elapsedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.printf("Completed in %.2f seconds.", elapsedTime);
        //39 tracks, 5 iterations, 81.42/70.17/81.67 seconds
    }
    public static void play(String path, ArrayList<Clip> clips) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        clips.add(clip);
    }
    public static String loop(int[] bounds, int[] originalBounds, int iteration) throws UnsupportedAudioFileException, LineUnavailableException {
        //There is a List to be able to stop all clips
        ArrayList<Clip> clips = new ArrayList<>();
        try {
            for (int i = 0; i <= round((float) (bounds[0] + bounds[1])/2); i++) {
                play("/Users/yuryvasiliev/IdeaProjects/TimedTests/src/Lost In Space.wav", clips);
            }
            //The following code will be run if the computer is able to run all tracks
            for (Clip i : clips) {
                stop(i);
            }
            if (bounds[1] - bounds[0] <= 2) {
                if (bounds[1] == originalBounds[1]) {
                    return "Your upper bound is too small. This took " + iteration + " iterations.";
                } else {
                    return bounds[0] + 1 + " tracks can be run at the same time on your computer. \nThis took " + iteration + " iterations.";
                }
            } else{
                bounds[0] = round((float) (bounds[0] + bounds[1])/2);
                System.out.println("On " + round((float) (bounds[0] + bounds[1]) / 2) + " tracks.");
                iteration++;
                return loop(bounds, originalBounds, iteration);
            }
        } catch (IOException e) {
            for (Clip i : clips) {
                stop(i);
            }
            if (bounds[1] - bounds[0] <= 2) {
                if (bounds[0] == originalBounds[0]) {
                    return "Your lower bound is too small. This took " + iteration + " iterations.";
                } else {
                    return bounds[0] + 1 + " tracks can be run at the same time on your computer. \nThis took " + iteration + " iterations.";
                }
            } else{
                bounds[1] = round((float) (bounds[0] + bounds[1])/2);
                System.out.println("On " + round((float) (bounds[0] + bounds[1]) / 2) + " tracks.");
                iteration++;
                return loop(bounds, originalBounds, iteration);
            }
        }
    }
    public static void stop(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
