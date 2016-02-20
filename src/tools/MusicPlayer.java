package tools;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author josh
 * @date 18/02/16.
 */
public class MusicPlayer  {
    private static ArrayList<ClipHolder> clips = new ArrayList<>();
    private final static String DIR = "assets" + Constants.SEP + "audio" + Constants.SEP ;
    private static final boolean play = true;

    public static void play(String fileName) {
        play(fileName, false);
    }

    public static void play(String fileName, boolean loop) {
        if (play) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(new File(DIR + fileName));

                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                if (loop)
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                else
                    clip.start();

                clips.add(new ClipHolder(fileName, clip));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop(String fileName) {
        ClipHolder clipHolder = null;
        for (int i = 0; i < clips.size(); i++) {
            clipHolder = clips.get(i);
            if (clipHolder.fileName.equals(fileName)) {
                clipHolder.clip.close();
                clips.remove(clipHolder);
            }
        }

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
