package c.genius.pushnotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

/**
 * Created by Haydar Dzaky S on 3/22/2018.
 */

public class NotificationHelper extends ContextWrapper{

    public static final String channelId = "channelId";
    public static final String channelName = "channelName";

    public static final String titleWeekday = "Jadwal Belajar Anda";
    public static final String messageWeekday = "Waktunya anda belajar dengan ALT";

    public static final String titleWeekend = "Jadwal Murojaah";
    public static final String messageWeekend = "Waktunya anda mengulang yang telah dipelajari";



    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {

        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorAccent);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message){

        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_alt)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);


    }
}
