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
import android.provider.BaseColumns;

public class AlarmDAO {

    public static final String TABLE = "alarm";

    public static class Columns implements BaseColumns {

        public static final String EXTERNAL_ID = "external_id";
        public static final String HOUR = "hour";
        public static final String MINUTES = "minutes";
        public static final String TIME = "time";
        public static final String ENABLED = "enabled";
        public static final String TITLE = "title";
        public static final String MESSAGE = "message";
        public static final String ALARM_HANDLE_LISTENER = "alarm_handle_listener";

        public static final int ID_INDEX = 0;
        public static final int EXTERNAL_ID_INDEX = 1;
        public static final int HOUR_INDEX = 2;
        public static final int MINUTES_INDEX = 3;
        public static final int TIME_INDEX = 4;
        public static final int ENABLED_INDEX = 5;
        public static final int TITLE_INDEX = 6;
        public static final int MESSAGE_INDEX = 7;
        public static final int ALARM_HANDLE_LISTENER_INDEX = 8;
    }


    private SQLiteOpenHelper mOpenHelper;


    // Constructors

    public AlarmDAO(Context context) {
        mOpenHelper = new Database(context);
    }


    // Public API

    public AlarmCursorWrapper select(String[] columns, String selection, String[] selectionArgs,
                                      String groupBy, String having, String orderBy) {

        if (columns == null) {
            columns = new String[]{
                    Columns._ID,
                    Columns.EXTERNAL_ID,
                    Columns.HOUR,
                    Columns.MINUTES,
                    Columns.TIME,
                    Columns.ENABLED,
                    Columns.TITLE,
                    Columns.MESSAGE,
                    Columns.ALARM_HANDLE_LISTENER
            };
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE, columns, selection, selectionArgs,
                groupBy, having, orderBy);

        return new AlarmCursorWrapper(cursor);
    }

    public AlarmCursorWrapper select(String selection, String[] selectionArgs) {

        Cursor cursor = select(null, selection, selectionArgs, null, null, Columns.TIME);

        return new AlarmCursorWrapper(cursor);
    }

    public AlarmCursorWrapper select() {

        Cursor cursor = select(null, null, null, null, null, Columns.TIME);

        return new AlarmCursorWrapper(cursor);
    }

    public Alarm selectById(long id) {
        return select(Columns._ID, id);
    }

    public Alarm selectByExternalId(long id) {
        return select(Columns.EXTERNAL_ID, id);
    }

    public long insert(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        ContentValues cv = toContentValues(alarm);

        long rowId = db.insert(TABLE, null, cv);
        if (rowId < 0) {
            throw new SQLException("Failed to insert Alarm " + cv);
        }

        return rowId;
    }

    public int update(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        long rowId = alarm.getId();

        ContentValues cv = toContentValues(alarm);

        String selection = Columns._ID + "=" + rowId;

        count = db.update(TABLE, cv, selection, null);

        return count;
    }

    public int delete(Alarm alarm) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        long rowId = alarm.getId();

        String selection = Columns._ID + "=" + rowId;

        count = db.delete(TABLE, selection, null);

        return count;
    }


    // Private API

    private Alarm select(String column, long id) {

        Cursor cursor = select(null, column + "=?", new String[]{Long.toString(id)},
                null, null, null);

        AlarmCursorWrapper wrapper = new AlarmCursorWrapper(cursor);

        if (wrapper.moveToFirst()) {
            return wrapper.getAlarm();
        } else {
            throw new SQLException("Failed to select Alarm with id " + id);
        }
    }

    private ContentValues toContentValues(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.EXTERNAL_ID, alarm.getExternalId());
        cv.put(Columns.HOUR, alarm.getHour());
        cv.put(Columns.MINUTES, alarm.getMinutes());
        cv.put(Columns.TIME, alarm.getTime());
        cv.put(Columns.ENABLED, alarm.isEnabled() ? 1 : 0);
        cv.put(Columns.TITLE, alarm.getTitle());
        cv.put(Columns.MESSAGE, alarm.getMessage());
        cv.put(Columns.ALARM_HANDLE_LISTENER, alarm.getAlarmHandleListener());

        return cv;
    }

}
