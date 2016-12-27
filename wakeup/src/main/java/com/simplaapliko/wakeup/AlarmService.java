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

        if (intent == null) {
            // nothing to do
            Log.d(TAG, "intent is null");
            return;
        }

        AlarmDAO alarmDAO = new AlarmDAO(this);

        // get alarm from extras
        long notFound = -1;
        long alarmId = intent.getLongExtra(AlarmController.EXTRA_ALARM_ID, notFound);

        // delete alarm from the database
        alarmDAO.delete(alarmId);

        // get listener using class name and reflection
        String handler = intent.getStringExtra(AlarmController.EXTRA_HANDLER);
        AlarmHandleListener listener = Util.getInstance(handler);

        // call onHandle
        if (listener != null) {
            listener.onHandle(this, intent);
        } else {
            Log.i(TAG, "not able to get new instance for class " + handler);
        }

        // Finish the execution from a previous startWakefulService(Context, Intent).
        // Any wake lock that was being held will now be released.
        AlarmReceiver.completeWakefulIntent(intent);
    }
}
