package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author josh
 * @date 18/02/16.
 */
public class MusicPlayer  {
    private static int musicPlayerCount = 0;
    private static boolean loop;
    //private static ConcurrentSafeArrayList<StreamHolder> playing = new ConcurrentSafeArrayList<>();
    private final static String DIR = "assets" + Constants.SEP + "audio" + Constants.SEP ;

    public static void playLoop(String fileName) {
        URL url = null;
        try {
            url =
                    new File("assets" + Constants.SEP + "audio" + Constants.SEP + fileName).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.
                    getAudioInputStream(url);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip.open(ais);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
