//Andrey Vasilyev July 5th, 2023
//Find the number of time it takes to play and stop playing 25 tracks
////Make sure to specify file path to the track on line 31

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class AudioTiming {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        long startTime = System.nanoTime();
        loop();
        double elapsedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.printf("Completed in %.2f seconds.", elapsedTime);
        //14.58, 6.93, 5.03, 7.76, 5.16 seconds
    }
    public static void play(String path, ArrayList<Clip> clips) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        clips.add(clip);
    }
    public static void loop() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //There is a List to be able to stop all clips
        ArrayList<Clip> clips = new ArrayList<>();
        for (int i = 0; i <= 25; i++) {
            play("/Users/yuryvasiliev/IdeaProjects/TimedTests/src/Lost In Space.wav", clips);
        }
        for (Clip clip : clips) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }
    }
}
