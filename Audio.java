//Andrey Vasilyev July 5th, 2023
//Finds the maximum number of audio tracks that can be run on a computer using binary sort algorithm
//Make sure to specify bounds on line 16 and 17 and the file path to the track on line 21
package Class_File;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.round;


public class Audio {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException {
        final long startTime = System.nanoTime();
        int[] bounds = new int[]{0, 50};
        //originalBounds are to see if the lower or upper bounds is too small or large
        final int[] originalBounds = new int[]{0, 50};
        System.out.println(loop(bounds, originalBounds, (byte) 0));
        System.out.printf("Completed in %.2f seconds.", (double) (System.nanoTime() - startTime) / 1000000000);
        //39 tracks, 5 iterations, 103.74/101.22 seconds
    }

    /**
     * Creates and starts a clip from a specified path
     * @param clips an ArrayList that collects all the newly created clips so that they can be stopped later
     */
    private static void play(ArrayList<Clip> clips) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        final Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File("/Users/yuriyvasiliev/IdeaProjects/PracticeProject/src/main/java/Other/Lost In Space.wav")));
        clip.start();
        clips.add(clip);
    }

    /**
     * Recursive function that keeps looping until the maximum number of tracks the computer can handle is determined.
     * @param bounds Uses binary sort and tries if the value in the middle of the bounds will work.
     * @param originalBounds Used to check if the bounds specified in main are reasonable.
     * @param iteration Just for fun, calculates how many iterations of the binary sort algorithm it took the computer
     *                  to evaluate the maximum number of tracks it can play.
     * @return The function itself, with newly created bounds, the same original bounds, and an incremented iteration.
     */
    private static String loop(int[] bounds, int[] originalBounds, byte iteration) {
        //There is a List to be able to stop all clips
        ArrayList<Clip> clips = new ArrayList<>();
        try {
            for (int i = 0; i <= round((float) (bounds[0] + bounds[1])/2); i++) {
                play(clips);
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
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    private static void stop(Clip clip) {
        clip.stop(); clip.close();
    }
}
