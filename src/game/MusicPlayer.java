package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author josh
 * @date 18/02/16.
 */
public class MusicPlayer  {
    private static int musicPlayerCount = 0;
    private static boolean loop;
    private static ArrayList<ClipHolder> clips = new ArrayList<>();
    private final static String DIR = "assets" + Constants.SEP + "audio" + Constants.SEP ;

    public static void playMusic(String fileName) {
        URL url = null;
        try {
            // getAudioInputStream() also accepts a File or InputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("assets" + Constants.SEP + "audio" + Constants.SEP + fileName));

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clips.add(new ClipHolder(fileName, clip));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic(String fileName) {
        ClipHolder clipHolder2 = null;
        for (ClipHolder clipHolder : clips) {
            if (clipHolder.fileName.equals(fileName)) {
                clipHolder.clip.close();
                clipHolder2 = clipHolder;
                break;

            }
        }
        if (clipHolder2!=null)
        clips.remove(clipHolder2);
    }

    private static class ClipHolder {
        private String fileName;
        private Clip clip;

        public ClipHolder(String fileName, Clip clip) {
            this.fileName = fileName;
            this.clip = clip;
        }
    }
}
