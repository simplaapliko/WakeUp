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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TimePicker;

import com.simplaapliko.wakeup.sample.R;

import java.util.Calendar;

public class DialogDateTime extends DialogFragment {

    private static final String TAG = "DialogDateTime";

    private Calendar mCalendar;
    protected View mRootView;

    public static DialogDateTime newInstance() {
        Log.d(TAG, "newInstance()");

        DialogDateTime fragment = new DialogDateTime();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DialogDateTime() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        mCalendar = Calendar.getInstance();

        // Disable back button click. Otherwise activity will also receive KEYCODE_BACK event
        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog()");

        // get root view
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_time, null);

        initUiWidgets(mRootView);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mRootView);

        // set buttons
        builder.setPositiveButton(
                android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick(), positive button");

                        sendResult(Dialog.BUTTON_POSITIVE);
                    }
                });

        builder.setNegativeButton(
                android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick(), negative button");

                        sendResult(Dialog.BUTTON_NEGATIVE);
                    }
                });

        return builder.show();
    }

    private void sendResult(int resultCode) {
        Log.d(TAG, "sendResult()");

        Intent intent = new Intent();
        intent.putExtra(MainFragment.SELECTED_DATE_EXTRA, mCalendar.getTime());

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void initUiWidgets(View rootView) {
        Log.d(TAG, "initUiWidgets()");

        // set up tabhost
        TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        // adding tabs
        tabSpec = tabHost.newTabSpec("date");
        tabSpec.setIndicator("Date");
        tabSpec.setContent(R.id.date);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("time");
        tabSpec.setIndicator("Time");
        tabSpec.setContent(R.id.time);
        tabHost.addTab(tabSpec);

        TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.time);
        timePicker.setIs24HourView(true); //set to true, because it is more compact
        timePicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        // init time picker before OnTimeChangedListener() is set
        // otherwise minutes will be set to current time, once setCurrentHour() is called
        timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.SECOND, 0);
            }
        });

        DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.date);
        datePicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        datePicker.init(
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                }
        );
    }
}