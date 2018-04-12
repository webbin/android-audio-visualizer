package project.webbin.mainapp;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by webbin on 18-4-12.
 */

public class GlobalPlayerCenter {

    private static GlobalPlayerCenter globalPlayerCenter;
    private static MediaPlayer mediaPlayer;


    GlobalPlayerCenter() {

    }

    public static void init() {
        mediaPlayer = new MediaPlayer();
    }

    public static void stop() {
        mediaPlayer.start();
    }

    public static void pause() {
        mediaPlayer.pause();
    }
    public static void start() {
        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void next() {

    }

    public static void last() {

    }

    public static void destroy() {
        mediaPlayer.release();
    }

}
