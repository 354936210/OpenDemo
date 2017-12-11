package com.owangwang.opendemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
//com.alibaba.android.rimet  包名
    //com.alibaba.android.rimet.biz.SplashActivity  入口Activity
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    /**
     * 是否初次启动
     */
    private boolean isInitialStartUp;
    /**
     * 是否已设置定时时间
     */
    private boolean isSetTime;
    private Button bt_open;
    /**
     * 设置定时分钟的EditText
     */
    EditText et_m;
    /**
     * 设置定时小时的EditText
     */
    EditText et_h;

    TextView tv_m;
    TextView tv_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        bt_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY,8);
                int interval=1000*30;//半分钟的间隔
                long triggerAtTime = System.currentTimeMillis();
                Intent i = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
                if (isSetTime){
                    manager.cancel(pi);
                    Toast.makeText(MainActivity.this,"取消定时成功!",Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString("time_m",et_m.getText().toString());
                    AlarmReceiver.mTime=et_h.getText().toString();
                    editor.putString("time_h",et_h.getText().toString());
                    editor.putBoolean("isSetTime",true);
                    if (isInitialStartUp){
                        //设置  程序已经不是初次使用
                        editor.putBoolean("isInitialStartUp",false);
                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                        ComponentName cn = new ComponentName("com.alibaba.android.rimet", "com.alibaba.android.rimet.biz.SplashActivity");
                        intent1.setComponent(cn);
                        startActivityForResult(intent1,122);
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime,interval, pi);
                        Toast.makeText(MainActivity.this,"设置定时成功!",Toast.LENGTH_SHORT).show();
                    }else {
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime,interval, pi);
                        Toast.makeText(MainActivity.this,"设置定时成功!",Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }

    private void init() {
        bt_open=findViewById(R.id.bt_open);
        et_m=findViewById(R.id.et_m);
        et_h=findViewById(R.id.et_h);
        tv_m=findViewById(R.id.tv_m);
        tv_h=findViewById(R.id.tv_h);
        pref=getSharedPreferences("app_config",MODE_PRIVATE);
        editor=pref.edit();
        isInitialStartUp=pref.getBoolean("isInitialStartUp",true);
        isSetTime=pref.getBoolean("isSetTime",false);
        if (isSetTime){
            tv_m.setText(pref.getString("time_m","出现错误!"));
            tv_h.setText(pref.getString("time_h","出现错误!"));
            bt_open.setText("取消设置");
        }else {
            bt_open.setText("设置定时");
        }

    }
}
