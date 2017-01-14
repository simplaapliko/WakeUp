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

package com.simplaapliko.wakeup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmController {

    public static final String ALARM_ALERT_ACTION = "com.simplaapliko.wakeup.ALARM_ALERT_ACTION";

    public static final String EXTRA_ALARM_ID = "com.simplaapliko.wakeup.EXTRA_ALARM_ID";
    public static final String EXTRA_EXTERNAL_ID = "com.simplaapliko.wakeup.EXTRA_EXTERNAL_ID";
    public static final String EXTRA_TITLE = "com.simplaapliko.wakeup.EXTRA_TITLE";
    public static final String EXTRA_MESSAGE = "com.simplaapliko.wakeup.EXTRA_MESSAGE";
    public static final String EXTRA_WHEN = "com.simplaapliko.wakeup.EXTRA_WHEN";
    public static final String EXTRA_HANDLER = "com.simplaapliko.wakeup.EXTRA_HANDLER";

    private static final String TAG = "AlarmController";

    public AlarmController() {}

    public void setAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "setAlarm: " + alarm);

        long id = new AlarmDAO(context)
                .insert(alarm);

        alarm.setId(id);

        enableAlarm(context, alarm);
    }

    public void cancelAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "cancelAlarm: " + alarm);

        int count = new AlarmDAO(context)
                .delete(alarm);

        if (count > 0) {
            disableAlarm(context, alarm);
        }
    }

    /**
     * @param context The context to use.
     * @param externalId External id to be disabled
     */
    public void cancelAlarm(Context context, long externalId) {
        Log.d(TAG, "cancelAlarm by externalId: " + externalId);

        Alarm alarm = new AlarmDAO(context)
                .selectByExternalId(externalId);

        cancelAlarm(context, alarm);
    }

    public void enableAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "enableAlarm: " + alarm);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ALARM_ALERT_ACTION);
        intent.putExtra(EXTRA_ALARM_ID, alarm.getId());
        intent.putExtra(EXTRA_EXTERNAL_ID, alarm.getExternalId());
        intent.putExtra(EXTRA_TITLE, alarm.getTitle());
        intent.putExtra(EXTRA_MESSAGE, alarm.getMessage());
        intent.putExtra(EXTRA_WHEN, alarm.getTime());
        intent.putExtra(EXTRA_HANDLER, alarm.getAlarmHandleListener());

        int alarmId = (int) alarm.getId();
        long triggerAt = alarm.getTime();

        PendingIntent sender = PendingIntent.getBroadcast(
                context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm.isExact()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, sender);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, sender);
            } else {
                alarmManager.set(alarm.getType(), triggerAt, sender);
            }
        } else {
            alarmManager.set(alarm.getType(), triggerAt, sender);
        }
    }

    public void disableAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "disableAlarm: " + alarm);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ALARM_ALERT_ACTION);

        int alarmId = (int) alarm.getId();

        PendingIntent sender = PendingIntent.getBroadcast(
                context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        sender.cancel();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
