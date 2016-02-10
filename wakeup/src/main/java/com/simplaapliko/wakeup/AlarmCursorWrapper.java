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

import android.database.Cursor;
import android.database.CursorWrapper;

public class AlarmCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    // Public API

    public Alarm getAlarm() {
        if (isBeforeFirst() || isAfterLast()) {
            return null;
        }

        int id = getInt(Alarm.Columns.ID_INDEX);
        int externalId = getInt(Alarm.Columns.EXTERNAL_ID_INDEX);
        int hour = getInt(Alarm.Columns.HOUR_INDEX);
        int minutes = getInt(Alarm.Columns.MINUTES_INDEX);
        long time = getLong(Alarm.Columns.TIME_INDEX);
        boolean enabled = getInt(Alarm.Columns.ENABLED_INDEX) == 1;
        String title = getString(Alarm.Columns.TITLE_INDEX);
        String message = getString(Alarm.Columns.MESSAGE_INDEX);
        String alarmHandleListener = getString(Alarm.Columns.ALARM_HANDLER_LISTENER_INDEX);

        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setExternalId(externalId);
        alarm.setHour(hour);
        alarm.setMinutes(minutes);
        alarm.setTime(time);
        alarm.setEnabled(enabled);
        alarm.setTitle(title);
        alarm.setMessage(message);
        alarm.setAlarmHandleListener(alarmHandleListener);

        return alarm;
    }

}
