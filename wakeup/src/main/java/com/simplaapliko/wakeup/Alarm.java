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

import android.app.AlarmManager;

public class Alarm {

    /**
     * A reference to {@link android.app.AlarmManager#RTC_WAKEUP}
     */
    public static final int RTC_WAKEUP = AlarmManager.RTC_WAKEUP;

    /**
     * A reference to {@link android.app.AlarmManager#RTC}
     */
    public static final int RTC = AlarmManager.RTC;

    /**
     * A reference to {@link android.app.AlarmManager#ELAPSED_REALTIME_WAKEUP}
     */
    public static final int ELAPSED_REALTIME_WAKEUP = AlarmManager.ELAPSED_REALTIME_WAKEUP;

    /**
     * A reference to {@link android.app.AlarmManager#ELAPSED_REALTIME}
     */
    public static final int ELAPSED_REALTIME = AlarmManager.ELAPSED_REALTIME;

    public static final int NOT_SET = -1;

    private long mId;
    private long mExternalId;
    private boolean mExact;
    private int mType;
    private long mTime;
    private boolean mEnabled;
    private String mTitle;
    private String mMessage;
    private boolean mKeepAfterReboot;
    private String mAlarmHandleListener;

    public Alarm() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getExternalId() {
        return mExternalId;
    }

    public void setExternalId(long externalId) {
        mExternalId = externalId;
    }

    public boolean isExact() {
        return mExact;
    }

    public void setExact(boolean exact) {
        mExact = exact;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public boolean isKeepAfterReboot() {
        return mKeepAfterReboot;
    }

    public void setKeepAfterReboot(boolean keepAfterReboot) {
        mKeepAfterReboot = keepAfterReboot;
    }

    public String getAlarmHandleListener() {
        return mAlarmHandleListener;
    }

    public void setAlarmHandleListener(String alarmHandleListener) {
        mAlarmHandleListener = alarmHandleListener;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "mId=" + mId +
                ", mExternalId=" + mExternalId +
                ", mExact=" + mExact +
                ", mType=" + mType +
                ", mTime=" + mTime +
                ", mEnabled=" + mEnabled +
                ", mTitle='" + mTitle + '\'' +
                ", mMessage='" + mMessage + '\'' +
                ", mKeepAfterReboot=" + mKeepAfterReboot +
                ", mAlarmHandleListener='" + mAlarmHandleListener + '\'' +
                '}';
    }
}
