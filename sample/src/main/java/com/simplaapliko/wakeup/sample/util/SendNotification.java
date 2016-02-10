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

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.AlarmController;
import com.simplaapliko.wakeup.AlarmHandleListener;
import com.simplaapliko.wakeup.sample.R;
import com.simplaapliko.wakeup.sample.ui.MainActivity;

public class SendNotification extends AlarmHandleListener {

    @Override
    public void onHandle(Context context, Intent intent) {

        Alarm alarm = intent.getParcelableExtra(AlarmController.ALARM_EXTRA);
        int smallIcon = R.mipmap.ic_launcher;

        int id = alarm.getExternalId();
        String title = alarm.getTitle();
        String text = alarm.getMessage();
        long when = alarm.getTime();

        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            Intent receiver = new Intent(context, MainActivity.class);

            PendingIntent pi = PendingIntent.getActivity(context, 0, receiver, 0);

            notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setSmallIcon(smallIcon)
                    .setContentIntent(pi)
                    .build();

            if (when != Alarm.NOT_SET) {
                notification.when = when;
            }

            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.ledARGB = 0xffffd700;
            notification.ledOnMS = 300;
            notification.ledOffMS = 1000;
        } else {
            //TODO
            throw new IllegalStateException("sample app currently doesn't support api < 16");
        }

        NotificationManager nm = getNotificationManager(context);
        nm.notify(id, notification);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
