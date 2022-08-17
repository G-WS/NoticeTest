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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        /**
         + 1、 构建通知渠道，传入三个参数
         + param channelId      渠道 Id，全局唯一即可
         + param channelName    渠道名称，显示给用户，需要清楚表达渠道的用途
         + param importance     通知的重要等级，不同的通知等级决定通知的不同行为，
         + 从高到底主要有 IMPORTANCE_HIGH、IMPORTANCE_DEFAULT、IMPORTANCE_LOW、IMPORTANCE_MIN这几种
         + 当然这里只是初始状态下的重要等级，用户可以随时手动更改某个通知渠道的重要等级，开发者是无法干预的。
         */
        //通知渠道的name，自己设定
        String name = getString(R.string.channel_name);
        //通知渠道的id
        String id = "my_channel_01";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(notificationChannel);

        }
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        //第二次尝试
        //创建通知渠道
        // Context.getSystemService()，该方法接收一个字符串参数用于确定获取系统的哪个服务。

        /**
         * 调用 AndroidX 中的兼容 API 方法，NotificationCompat.Builder()
         * param  context
         * param  channelId   渠道Id，需要和我们创建的渠道 Id 相匹配
         */
        //                    Notification notification =
        //                            new NotificationCompat.Builder(context, channelId);
        // 上述对象在调用最终的 build() 方法之前可以连缀任意多的设置方法来创建一个丰富的 Notification 对象

        //通知渠道的id
        String id = "my_channel_01";
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        Notification notification = new NotificationCompat.Builder(MainActivity.this, id)
                // 指定通知的标题内容，下拉状态栏显示
                .setContentTitle("This is content title")
                // 指定通知的正文内容，下拉状态栏显示
                .setContentText("This is content text")
                // 设置通知的小图标，显示在状态栏上，只能使用纯 alpha 图层的图片
                .setSmallIcon(R.drawable.apple)
                .setContentIntent(pendingIntent)
                //设置是否点击后消失
                .setAutoCancel(true)

                // 设置通知的大图标，下拉状态栏显示
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.apple_large))
//                //设置震动
//                .setVibrate(new long[]{0, 1000, 1000, 1000})
                //根据手机环境决定
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        mNotificationManager.notify(1, notification);
    }
}
