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

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AlarmService extends IntentService {

    private static final String TAG = "AlarmService";

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");

        // get alarm from extras
        Alarm alarm = intent.getParcelableExtra(AlarmController.EXTRA_ALARM);

        // delete alarm from the database
        new AlarmDAO(this).delete(alarm);

        // get listener using class name and reflection
        AlarmHandleListener listener = Util.getInstance(alarm.getAlarmHandleListener());

        // call onHandle
        if (listener != null) {
            listener.onHandle(this, intent);
        } else {
            Log.i(TAG, "not able to get new instance for class " + alarm.getAlarmHandleListener());
        }

        // Finish the execution from a previous startWakefulService(Context, Intent).
        // Any wake lock that was being held will now be released.
        AlarmReceiver.completeWakefulIntent(intent);
    }

}
