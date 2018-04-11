package project.webbin.mainapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.media.audiofx.Visualizer;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.DataOutputStream;
import java.util.List;

import project.webbin.mainapp.base.BaseAppcompatActivity;

public class MainActivity extends BaseAppcompatActivity {

    private final static int APPLY_STORAGE_PERMISSION = 1111;

    private Visualizer visualizer;
    private AudioManager audioManager;
    private MediaSessionManager mediaSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (applyPermission()) {
            init();
        }
    }

    private void init() {

        String apkRoot="chmod 777 "+getPackageCodePath();
        if (!RootCommand(apkRoot)) finish();
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSessionManager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
            assert mediaSessionManager != null;
            List<MediaController> list = mediaSessionManager.getActiveSessions(null);
            log("media controller list length = " + String.valueOf(list.size()));
            showControllers(list);
            mediaSessionManager.addOnActiveSessionsChangedListener(new MediaSessionManager.OnActiveSessionsChangedListener() {
                @Override
                public void onActiveSessionsChanged(@Nullable List<MediaController> list) {
                    showControllers(list);
                }
            }, null);
        } else {
//            RemoteControlClient client = new RemoteControlClient();
//            audioManager.registerRemoteControlClient();
        }

        captureMediaPlayer();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showControllers(List<MediaController> list) {
        for (MediaController controller : list) {
            log("controller name:" + controller.getPackageName());
        }
    }

    private void getPlayBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.getActivePlaybackConfigurations();
        }
    }

    private void captureMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        Visualizer.OnDataCaptureListener dataCaptureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {
                log(String.valueOf(bytes.length));
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {

            }
        };
        visualizer.setDataCaptureListener(dataCaptureListener,
                Visualizer.getMaxCaptureRate() / 2,
                true,
                false);
        visualizer.setEnabled(true);
    }

    protected boolean applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MEDIA_CONTENT_CONTROL,
            };
            boolean allPermission = false;
            for (String p : permissions) {
                if (checkSelfPermission(p) == PackageManager.PERMISSION_GRANTED)
                    allPermission = true;
            }
            if (!allPermission) {
                ActivityCompat.requestPermissions(this, permissions, APPLY_STORAGE_PERMISSION);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == APPLY_STORAGE_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                toast("获取权限失败");
            } else {
                init();
            }
        }
    }


    public boolean RootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");

            os = new DataOutputStream(process.getOutputStream());

            os.writeBytes(command + "\n");

            os.writeBytes("exit\n");

            os.flush();

            process.waitFor();
        } catch (Exception e) {
            Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        log("*** DEBUG ***", "Root SUC ");
        return true;

    }


}
