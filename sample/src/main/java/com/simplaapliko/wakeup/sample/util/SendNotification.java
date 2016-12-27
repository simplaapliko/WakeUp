/*
 * Copyright (C) 2016 Oleg Kan, @Simplaapliko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.wakeup.sample.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.AlarmController;
import com.simplaapliko.wakeup.AlarmHandleListener;
import com.simplaapliko.wakeup.sample.R;
import com.simplaapliko.wakeup.sample.ui.MainActivity;

public class SendNotification extends AlarmHandleListener {

    private static final String TAG = "SendNotification";

    @Override
    public void onHandle(Context context, Intent intent) {
        Log.d(TAG, "onHandle");

        if (intent == null) {
            // nothing to do
            Log.d(TAG, "intent is null");
            return;
        }

        long alarmId = intent.getLongExtra(AlarmController.EXTRA_ALARM_ID, -1);
        long externalId = intent.getLongExtra(AlarmController.EXTRA_EXTERNAL_ID, -1);
        String title = intent.getStringExtra(AlarmController.EXTRA_TITLE);
        String text = intent.getStringExtra(AlarmController.EXTRA_MESSAGE);
        long when = intent.getLongExtra(AlarmController.EXTRA_WHEN, Alarm.NOT_SET);

        int smallIcon = R.mipmap.ic_launcher;

        int notificationId = (int) alarmId;

        Notification notification;

        Intent receiver = new Intent(context, MainActivity.class);
        receiver.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        receiver.putExtra(MainActivity.ALARM_ID_KEY, externalId);

        PendingIntent pi = PendingIntent.getActivity(context, notificationId, receiver, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon(smallIcon)
                .setContentIntent(pi);

        if (when != Alarm.NOT_SET) {
            builder.setWhen(when);
        }

        builder.setLights(0xFFFF0000, 1000, 4000);
        builder.setSound(Uri.parse("content://media/internal/audio/media/33"));
        builder.setVibrate(new long[]{1000, 500, 500, 500});

        notification = builder.build();
        //notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        //notification.defaults |= Notification.DEFAULT_LIGHTS;
        //notification.defaults |= Notification.DEFAULT_SOUND;
        //notification.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationManager nm = getNotificationManager(context);
        nm.notify(notificationId, notification);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
