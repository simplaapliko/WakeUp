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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Util {

    private static final String TAG = "Util";

    public static AlarmHandleListener getInstance(String className) {
        
        if (className == null) {
            return null;
        }

        Object instance = null;

        //noinspection TryWithIdenticalCatches
        try {
            Class cl = Class.forName(className);
            @SuppressWarnings("unchecked")
            Constructor constructor = cl.getConstructor();
            instance = constructor.newInstance();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return (AlarmHandleListener) instance;
    }
}
