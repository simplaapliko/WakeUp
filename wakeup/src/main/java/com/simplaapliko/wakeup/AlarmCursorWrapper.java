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

        long id = getLong(AlarmDAO.Columns.ID_INDEX);
        long externalId = getLong(AlarmDAO.Columns.EXTERNAL_ID_INDEX);
        boolean exact = getInt(AlarmDAO.Columns.IS_EXACT_INDEX) == 1;
        int type = getInt(AlarmDAO.Columns.TYPE_INDEX);
        long time = getLong(AlarmDAO.Columns.TIME_INDEX);
        boolean enabled = getInt(AlarmDAO.Columns.ENABLED_INDEX) == 1;
        String title = getString(AlarmDAO.Columns.TITLE_INDEX);
        String message = getString(AlarmDAO.Columns.MESSAGE_INDEX);
        boolean keepAfterReboot = getInt(AlarmDAO.Columns.KEEP_AFTER_REBOOT_INDEX) == 1;
        String alarmHandleListener = getString(AlarmDAO.Columns.ALARM_HANDLE_LISTENER_INDEX);

        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setExternalId(externalId);
        alarm.setExact(exact);
        alarm.setType(type);
        alarm.setTime(time);
        alarm.setEnabled(enabled);
        alarm.setTitle(title);
        alarm.setMessage(message);
        alarm.setKeepAfterReboot(keepAfterReboot);
        alarm.setAlarmHandleListener(alarmHandleListener);

        return alarm;
    }

}
