package com.example.noticetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_notice:
                //创建NotificationManager对通知进行管理
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //创建渠道并设置器重要性
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    //通知渠道的name，自己设定
                    String name = getString(R.string.channel_name);
                    //通知渠道的id
                    String id = "my_channel_01";
                    NotificationChannel channel = new NotificationChannel(name,id,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                    //设置点击事件
//                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
//                    PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,intent,0);
//                    //创建通知内容，ID必须在实例化Notification的时候对应上，否则通知栏无效，系统则会将Toast放入一个错误
                    Notification notification = new NotificationCompat.Builder(MainActivity.this,"my_channel_01")
                            .setContentTitle("My notification")             //设置标题
                            .setContentText("Hello World!")                 //设置正文
                            .setWhen(System.currentTimeMillis())          //设置被创建的时间
                            .setSmallIcon(R.mipmap.ic_launcher)             //设置通知的小图标
                            .setAutoCancel(true)
//                            .setContentIntent(pi)//点击时通知取消
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                    R.mipmap.ic_launcher))    //设置通知的大图标
                            .build();

                    manager.notify(1,notification);


                }

        }
    }
}