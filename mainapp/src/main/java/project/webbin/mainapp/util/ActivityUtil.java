package project.webbin.mainapp.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by webbin on 18-4-12.
 */

public class ActivityUtil {


    public static void startActivity(Context context, Class actClass) {
        Intent intent = new Intent(context, actClass);
        context.startActivity(intent);
    }
}
