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
import android.util.Log;

public class AlarmController {

    public static final String ALARM_ALERT_ACTION = "com.simplaapliko.wakeup.ALARM_ALERT_ACTION";

    public static final String ALARM_EXTRA = "com.simplaapliko.wakeup.ALARM_EXTRA";

    private static final String TAG = "AlarmController";


    // Constructors

    public AlarmController() {}


    // Public API

    public void setAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "setAlarm");

        int id = new AlarmDAO(context)
                .insert(alarm);

        alarm.setId(id);

        enableAlarm(context, alarm);
    }

    public void cancelAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "cancelAlarm");

        int count = new AlarmDAO(context)
                .delete(alarm);

        if (count > 0) {
            disableAlarm(context, alarm);
        }
    }

    public void enableAlarm(Context context, Alarm alarm) {
        Log.d(TAG, "enableAlarm");

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(ALARM_ALERT_ACTION);
        intent.putExtra(ALARM_EXTRA, alarm);

        int alarmId = alarm.getExternalId();
        long triggerAt = alarm.getTime();

        PendingIntent sender = PendingIntent.getBroadcast(
                context, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, sender);
    }

    public void disableAlarm(Context context, Alarm alarm) {

        Log.d(TAG, "disableAlarm");
    }

}
