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

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.AlarmController;
import com.simplaapliko.wakeup.AlarmDAO;
import com.simplaapliko.wakeup.sample.R;
import com.simplaapliko.wakeup.sample.util.SendNotification;

import java.util.Calendar;
import java.util.Date;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int REQUEST_SELECT_DATE = 0;
    public static final String SELECTED_DATE_EXTRA = "com.simplaapliko.wakeup.sample.SELECTED_DATE_EXTRA";

    private static final int CONTEXT_MENU_DELETE = 0;

    private static final String PREF_EXTERNAL_ID = "external_id";

    private AppCompatCheckBox mExactCheckBox;
    private ListView mList;
    private long mId;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mId = getExternalId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mExactCheckBox = (AppCompatCheckBox) rootView.findViewById(R.id.exact);
        mList = (ListView) rootView.findViewById(R.id.list);
        registerForContextMenu(mList);

        Button insert = (Button) rootView.findViewById(R.id.set);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDateTime dialog = DialogDateTime.newInstance();
                dialog.setTargetFragment(MainFragment.this, REQUEST_SELECT_DATE);
                dialog.show(getFragmentManager(), null);
            }
        });

        Button refresh = (Button) rootView.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartLoader();
            }
        });

        restartLoader();

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AlarmCursorLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        AlarmAdapter adapter = new AlarmAdapter(getContext(), new AlarmDAO(getContext()).select());
        mList.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mList.setAdapter(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Dialog.BUTTON_NEGATIVE) {
            return;
        }

        Date date = (Date) data.getSerializableExtra(SELECTED_DATE_EXTRA);
        setAlarm(date);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, CONTEXT_MENU_DELETE, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                long id = info.id;
                Alarm alarm = new AlarmDAO(getContext()).selectById(id);

                new AlarmController().cancelAlarm(getContext(), alarm);

                restartLoader();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    private void setAlarm(Date date) {
        mId++;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        Alarm alarm = new Alarm();
        alarm.setExternalId(mId);
        alarm.setExact(mExactCheckBox.isChecked());
        alarm.setType(Alarm.RTC_WAKEUP);
        alarm.setTime(calendar.getTimeInMillis());
        alarm.setEnabled(true);
        alarm.setTitle("Title");
        alarm.setMessage("Notification message, externalId: " + mId);
        alarm.setAlarmHandleListener(SendNotification.class.getCanonicalName());

        new AlarmController().setAlarm(getContext(), alarm);

        restartLoader();

        saveExternalId(mId);
    }

    private long getExternalId() {
        return getActivity().getPreferences(Context.MODE_PRIVATE)
                .getLong(PREF_EXTERNAL_ID, 0);
    }

    private void saveExternalId(long id) {
        getActivity().getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putLong(PREF_EXTERNAL_ID, id)
                .apply();
    }

    private static class AlarmCursorLoader extends CursorLoader {

        private Cursor mCursor;

        public AlarmCursorLoader(Context context) {
            super(context);

        }

        @Override
        public Cursor loadInBackground() {

            mCursor = initCursor();

            return mCursor;
        }

        @Override
        protected void onStopLoading() {
            super.onStopLoading();
            cancelLoad();
        }

        @Override
        public void onCanceled(Cursor cursor) {
            super.onCanceled(cursor);

            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        }

        @Override
        protected void onReset() {
            super.onReset();

            onStopLoading();

            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }

            mCursor = null;
        }

        private Cursor initCursor() {
            return new AlarmDAO(getContext()).select();
        }
    }
}
