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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive, action = " + intent.getAction());

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            AlarmCursorWrapper wrapper = new AlarmDAO(context).select();
            if (wrapper.moveToFirst()) {

                AlarmController alarmController = new AlarmController();
                AlarmDAO alarmDao = new AlarmDAO(context);
                long currentTime = System.currentTimeMillis();
                do {
                    Alarm alarm = wrapper.getAlarm();
                    if (alarm.getTime() > currentTime) {
                        alarmController.enableAlarm(context, alarm);
                    } else {
                        alarmDao.delete(alarm);
                    }
                } while (wrapper.moveToNext());
            }
        }
    }

}
