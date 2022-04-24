/*
 * Copyright 2022 Russell Wolf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.russhwolf.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

private val context: Context = ApplicationProvider.getApplicationContext()
private val factory = KeystoreSettings.Factory(context)

@RequiresApi(Build.VERSION_CODES.M)
@RunWith(AndroidJUnit4::class)
class KeystoreSettingsTest : BaseSettingsTest(factory) {

    @Test
    fun constructor_sharedPreferences() {
        val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val settings = KeystoreSettings(preferences)

        preferences.edit().putInt("a", 3).apply()
        assertEquals(3, settings["a", 0])
    }

    @Test
    fun constructor_commit() {
        val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val settings = KeystoreSettings(preferences, commit = true)

        settings.putInt("a", 3)
        assertEquals(3, preferences.getInt("a", -1))
    }


    @Test
    fun constructor_noCommit() {
        val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val settings = KeystoreSettings(preferences, commit = false)

        settings.putInt("a", 3)
        assertEquals(3, preferences.getInt("a", -1))
    }

    @Test
    fun factory_name() {
        val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val settings = factory.create("Settings")

        preferences.edit().putInt("a", 3).apply()
        assertEquals(3, settings["a", 0])
    }

    @Test
    fun factory_noName() {
        val preferences = context.getSharedPreferences("com.russhwolf.settings.test_preferences", Context.MODE_PRIVATE)
        val settings = factory.create()

        preferences.edit().putInt("a", 3).apply()
        assertEquals(3, settings["a", 0])
    }
}
