package com.owangwang.opendemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
String TAG="AlarmReceiver";
static String mTime;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        SharedPreferences pref=context.getSharedPreferences("app_config",MODE_PRIVATE);
//        //读取配置文件中设置的定时时间如果不存在 那么默认为08-00也就是8点整
//        String mtime=pref.getString("time_h","08");

        if (showDate().equals(mTime)){
            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.alibaba.android.rimet", "com.alibaba.android.rimet.biz.SplashActivity");
            intent1.setComponent(cn);
            context.startActivity(intent1);
            Log.d(TAG,"启动成功");
        }else {
            Log.d(TAG,"条件不符");
        }

    }
    public static String showDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");
        String date = sDateFormat.format(new Date());
        return date;
    }
}
