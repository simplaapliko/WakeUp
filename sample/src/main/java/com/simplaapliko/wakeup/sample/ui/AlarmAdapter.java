/*
 * Copyright (C) 2016 Oleg Kan
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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.AlarmCursorWrapper;
import com.simplaapliko.wakeup.sample.R;

import java.util.Date;

public class AlarmAdapter extends ResourceCursorAdapter {

    static class ViewHolder {
        TextView nameTextView;
        TextView dateTextView;
    }

    private LayoutInflater mLayoutInflater;

    public AlarmAdapter(Context context, Cursor c) {
        super(context, R.layout.list_item, c, 0);

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Alarm alarm = ((AlarmCursorWrapper) getItem(position)).getAlarm();

        holder.nameTextView.setText(alarm.toString());
        holder.dateTextView.setText(new Date(alarm.getTime()).toString());

        return convertView;
    }
}