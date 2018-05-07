package asus.com.example.asus.cardview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class NotificationC extends AppCompatActivity {
    Button buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_yazi_ekle);


        Bundle extras = getIntent().getExtras();
        String value = extras.getString("send_not");


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        Notification.Builder builder = new Notification.Builder(NotificationC.this);
        builder.setContentTitle(value);
        builder.setContentText("DO NOT FORGET!TOMORROW IS VERY SPECIAL DAY !!!");
        builder.setSmallIcon(R.drawable.ic_filter);
        builder.setAutoCancel(true);
        builder.setTicker("Bildirim Geliyor!!!!");

        Intent intent = new Intent(NotificationC.this, ForHediye.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationC.this, 1, intent, 0);

        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
        manager.notify(1, notification);


    }
}
