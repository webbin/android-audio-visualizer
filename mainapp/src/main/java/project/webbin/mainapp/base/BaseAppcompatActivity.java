package project.webbin.mainapp.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by webbin on 18-4-11.
 */

public class BaseAppcompatActivity extends AppCompatActivity {

    protected Context mContext=this;
    private Point windowSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowSize=new Point();
        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(windowSize);
        } else {
            windowSize = new Point();
            windowSize.x = display.getWidth();
            windowSize.y = display.getHeight();
        }
    }

    protected void toast(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
    protected void log(String message){
        Log.v(getClass().getSimpleName()+" --- ", message);
    }

    protected void logE(String message) {
        Log.e(getClass().getSimpleName()+" --- ", message);
    }

    protected int getScreenWidth(){
        return windowSize.x;
    }
    protected int getScreenHeight(){
        return windowSize.y;
    }



}
