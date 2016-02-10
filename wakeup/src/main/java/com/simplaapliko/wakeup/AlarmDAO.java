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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class AlarmDAO {

    private SQLiteOpenHelper mOpenHelper;


    public AlarmDAO(Context context) {
        mOpenHelper = new Database(context);
    }


    public AlarmCursorWrapper select() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(Alarm.TABLE);

        String[] projection = {
                Alarm.Columns._ID,
                Alarm.Columns.EXTERNAL_ID,
                Alarm.Columns.HOUR,
                Alarm.Columns.MINUTES,
                Alarm.Columns.TIME,
                Alarm.Columns.ENABLED,
                Alarm.Columns.TITLE,
                Alarm.Columns.MESSAGE,
                Alarm.Columns.ALARM_HANDLER_LISTENER
        };

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor = qb.query(db, projection, null, null,
                null, null, null);

        return new AlarmCursorWrapper(cursor);
    }

    public Alarm select(long rowId) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        String[] projection = {
                Alarm.Columns._ID,
                Alarm.Columns.EXTERNAL_ID,
                Alarm.Columns.HOUR,
                Alarm.Columns.MINUTES,
                Alarm.Columns.TIME,
                Alarm.Columns.ENABLED,
                Alarm.Columns.TITLE,
                Alarm.Columns.MESSAGE,
                Alarm.Columns.ALARM_HANDLER_LISTENER
        };

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(Alarm.TABLE);
        qb.appendWhere(Alarm.Columns._ID);
        qb.appendWhere(Long.toString(rowId));

        Cursor cursor = qb.query(db, projection, null, null,
                null, null, null);

        AlarmCursorWrapper wrapper = new AlarmCursorWrapper(cursor);

        if (wrapper.moveToFirst()) {
            return wrapper.getAlarm();
        } else {
            throw new SQLException("Failed to select Alarm with id " + rowId);
        }
    }

    public int insert(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Alarm.Columns.EXTERNAL_ID, alarm.getExternalId());
        cv.put(Alarm.Columns.HOUR, alarm.getHour());
        cv.put(Alarm.Columns.MINUTES, alarm.getMinutes());
        cv.put(Alarm.Columns.TIME, alarm.getTime());
        cv.put(Alarm.Columns.ENABLED, alarm.isEnabled() ? 1 : 0);
        cv.put(Alarm.Columns.TITLE, alarm.getTitle());
        cv.put(Alarm.Columns.MESSAGE, alarm.getMessage());
        cv.put(Alarm.Columns.ALARM_HANDLER_LISTENER, alarm.getAlarmHandleListener());

        int rowId = (int) db.insert(Alarm.TABLE, null, cv);
        if (rowId < 0) {
            throw new SQLException("Failed to insert Alarm " + cv);
        }

        return rowId;
    }

    public int update(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        int rowId = alarm.getId();

        ContentValues cv = new ContentValues();
        cv.put(Alarm.Columns.EXTERNAL_ID, alarm.getExternalId());
        cv.put(Alarm.Columns.HOUR, alarm.getHour());
        cv.put(Alarm.Columns.MINUTES, alarm.getMinutes());
        cv.put(Alarm.Columns.TIME, alarm.getTime());
        cv.put(Alarm.Columns.ENABLED, alarm.isEnabled() ? 1 : 0);
        cv.put(Alarm.Columns.TITLE, alarm.getTitle());
        cv.put(Alarm.Columns.MESSAGE, alarm.getMessage());
        cv.put(Alarm.Columns.ALARM_HANDLER_LISTENER, alarm.getAlarmHandleListener());

        String selection = Alarm.Columns._ID + "=" + rowId;

        count = db.update(Alarm.TABLE, cv, selection, null);

        return count;
    }

    public int delete(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        int rowId = alarm.getId();

        String selection = Alarm.Columns._ID + "=" + rowId;

        count = db.delete(Alarm.TABLE, selection, null);

        return count;
    }

}
