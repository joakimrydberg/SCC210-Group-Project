package game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import other.ConcurrentSafeArrayList;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author josh
 * @date 18/02/16.
 */
public class MusicPlayer implements Runnable {
    private static int musicPlayerCount = 0;
    private static boolean loop;
    private static ConcurrentSafeArrayList<StreamHolder> playing = new ConcurrentSafeArrayList<>();
    private final static String DIR = "assets" + Constants.SEP + "audio" + Constants.SEP ;

    public MusicPlayer() {
        throw new RuntimeException("MusicPlayer cannot be initiated");
    }

    public static void playSound(String filename) {
        SoundBuffer soundBuffer = new SoundBuffer();
        try {
            soundBuffer.loadFromFile(Paths.get(DIR + filename));
        } catch(IOException e) {
            e.printStackTrace();
        }

        Sound sound = new Sound();
        sound.setBuffer(soundBuffer);
        sound.play();
    }

    public static void playMusic(String fileName) {
        StreamHolder temp = new StreamHolder(fileName);
        playing.add(temp);
        AudioPlayer.player.start(temp.audioStream);
    }

    public static void stopMusic(String fileName) {
        StreamHolder streamHolder;
        for (int i = 0; i < playing.size(); i++) {
            streamHolder = playing.get(i);
            if (streamHolder.fileName.equals(fileName)) {
                playing.remove(streamHolder);
            }
        }
    }

    @Override
    public void run() {
        AudioStream audioStream;
        while (loop) {
            if (!Driver.getWindow().isOpen())
                stopAll();

            for (int i = 0; i < playing.size(); i++) {
                audioStream = playing.get(i).audioStream;
                try {
                    if (audioStream.available() == 0) {
                        AudioPlayer.player.start(audioStream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startAll() {
        if (!loop) {
            loop = true;
            new Thread(new MusicPlayer()).start();
        }
    }

    public static void stopAll() {
        loop = false;

        for (int i = 0; i < playing.size(); i++) {
            AudioPlayer.player.stop(playing.get(i).audioStream);
        }
    }

    private static class StreamHolder {
        private AudioStream audioStream;
        private String fileName;

        public StreamHolder(String fileName) {
            try {
                audioStream = new AudioStream(new FileInputStream(DIR + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.fileName = fileName;
        }
    }
}
