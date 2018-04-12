package project.webbin.mainapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import project.webbin.mainapp.base.BaseService;

/**
 * Created by webbin on 18-4-12.
 */

public class MainPlayerService extends BaseService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
