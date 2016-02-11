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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "com.simplaapliko.wakeup.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Alarm.TABLE + " (" +
                        Alarm.Columns._ID + " INTEGER PRIMARY KEY, " +
                        Alarm.Columns.EXTERNAL_ID + " INTEGER NOT NULL, " +
                        Alarm.Columns.HOUR + " INTEGER NOT NULL, " +
                        Alarm.Columns.MINUTES + " INTEGER NOT NULL, " +
                        Alarm.Columns.TIME + " INTEGER, " +
                        Alarm.Columns.ENABLED + " INTEGER NOT NULL, " +
                        Alarm.Columns.TITLE + " TEXT, " +
                        Alarm.Columns.MESSAGE + " TEXT, " +
                        Alarm.Columns.ALARM_HANDLE_LISTENER + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // currently nothing to update
    }
}