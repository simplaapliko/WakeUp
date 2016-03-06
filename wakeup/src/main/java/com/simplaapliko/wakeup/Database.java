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

    // Inner classes

    public static class Manager {

        private static Manager sManager;

        private Database mDatabase;


        // Constructors

        public static synchronized Manager getInstance(Context context){
            if (sManager == null) {
                sManager = new Manager(context);
            }
            return sManager;
        }

        private Manager(Context context) {
            mDatabase = new Database(context);
        }


        // Public API

        public SQLiteDatabase getWritableDatabase() {
            return mDatabase.getWritableDatabase();
        }

        public SQLiteDatabase getReadableDatabase() {
            return mDatabase.getReadableDatabase();
        }

        public void close() {
            mDatabase.close();
        }
    }

    private static final String DATABASE_NAME = "com.simplaapliko.wakeup.db";
    private static final int DATABASE_VERSION = 1;


    // Constructors

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Public API

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AlarmDAO.TABLE + " (" +
                        AlarmDAO.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        AlarmDAO.Columns.EXTERNAL_ID + " INTEGER NOT NULL, " +
                        AlarmDAO.Columns.IS_EXACT + " INTEGER NOT NULL, " +
                        AlarmDAO.Columns.TYPE + " INTEGER NOT NULL, " +
                        AlarmDAO.Columns.TIME + " INTEGER, " +
                        AlarmDAO.Columns.ENABLED + " INTEGER NOT NULL, " +
                        AlarmDAO.Columns.TITLE + " TEXT, " +
                        AlarmDAO.Columns.MESSAGE + " TEXT, " +
                        AlarmDAO.Columns.KEEP_AFTER_REBOOT + " INTEGER NOT NULL, " +
                        AlarmDAO.Columns.ALARM_HANDLE_LISTENER + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // currently nothing to update
    }
}