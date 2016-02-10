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

package com.simplaapliko.wakeup.sample;

import android.test.ApplicationTestCase;

import com.simplaapliko.wakeup.Alarm;
import com.simplaapliko.wakeup.AlarmDAO;
import com.simplaapliko.wakeup.sample.util.SendNotification;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AlarmDAOTest extends ApplicationTestCase<SampleApplication> {

    public AlarmDAOTest() {
        super(SampleApplication.class);
    }

    private static final int FAILED = -1;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        createApplication();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testInsertAlarmSuccessful() {
        int actualId = insert(1, 10, 20, System.currentTimeMillis(), true, "title", "new alarm");
        assertThat(actualId, is(not(equalTo(FAILED))));
    }

    private int insert(int id, int hour, int minutes, long time, boolean enabled, String title, String message) {
        AlarmDAO alarmDAO = new AlarmDAO(getContext());

        Alarm alarm = new Alarm();
        alarm.setExternalId(id);
        alarm.setHour(hour);
        alarm.setMinutes(minutes);
        alarm.setTime(time);
        alarm.setEnabled(enabled);
        alarm.setTitle(title);
        alarm.setMessage(message);
        alarm.setAlarmHandleListener(SendNotification.class.getCanonicalName());

        return alarmDAO.insert(alarm);
    }

}