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

package com.simplaapliko.wakeup.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.sample.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String ALARM_ID_KEY = "com.simplaapliko.wakeup.sample.ui.ALARM_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        long alarmId = intent.getLongExtra(ALARM_ID_KEY, Alarm.NOT_SET);
        if (alarmId != Alarm.NOT_SET) {
            Toast.makeText(this, "onCreate: external id received: " + alarmId, Toast.LENGTH_SHORT).show();
            intent.putExtra(ALARM_ID_KEY, Alarm.NOT_SET);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);

        long alarmId = intent.getLongExtra(ALARM_ID_KEY, Alarm.NOT_SET);
        if (alarmId != Alarm.NOT_SET) {
            Toast.makeText(this, "onNewIntent: external id received: " + alarmId, Toast.LENGTH_SHORT).show();
        }
    }
}
