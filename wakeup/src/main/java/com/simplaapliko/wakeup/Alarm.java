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

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Alarm implements Parcelable {

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

    public static final int NOT_SET = -1;

    private long mId;
    private long mExternalId;
    private int mHour;
    private int mMinutes;
    private long mTime;
    private boolean mEnabled;
    private String mTitle;
    private String mMessage;
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

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public void setMinutes(int minutes) {
        mMinutes = minutes;
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
                ", mHour=" + mHour +
                ", mMinutes=" + mMinutes +
                ", mTime=" + mTime +
                ", mEnabled=" + mEnabled +
                ", mTitle='" + mTitle + '\'' +
                ", mMessage='" + mMessage + '\'' +
                ", mAlarmHandleListener='" + mAlarmHandleListener + '\'' +
                '}';
    }


    // Parcelable

    protected Alarm(Parcel in) {
        mId = in.readLong();
        mExternalId = in.readLong();
        mHour = in.readInt();
        mMinutes = in.readInt();
        mTime = in.readLong();
        mEnabled = in.readInt() == 1;
        mTitle = in.readString();
        mMessage = in.readString();
        mAlarmHandleListener = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mExternalId);
        dest.writeInt(mHour);
        dest.writeInt(mMinutes);
        dest.writeLong(mTime);
        dest.writeInt(mEnabled ? 1 : 0);
        dest.writeString(mTitle);
        dest.writeString(mMessage);
        dest.writeString(mAlarmHandleListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
