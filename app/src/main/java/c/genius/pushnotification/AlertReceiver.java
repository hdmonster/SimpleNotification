package c.genius.pushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static c.genius.pushnotification.NotificationHelper.messageWeekday;
import static c.genius.pushnotification.NotificationHelper.titleWeekday;

/**
 * Created by Haydar Dzaky S on 3/22/2018.
 */

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(titleWeekday, messageWeekday);
        notificationHelper.getManager().notify(1, nb.build());

    }
}
