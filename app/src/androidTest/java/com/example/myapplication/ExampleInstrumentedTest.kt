package com.example.myapplication

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.db.DBHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)
    }
    @Test
    fun test_dataBase(){
        var db = DBHelper(InstrumentationRegistry.getInstrumentation().targetContext,null)
        db.addIngredient("testIngredient")
        var cursor = db.getIngredient()
        var ingredients = mutableListOf<String>()

        while (cursor!!.moveToNext()){
            ingredients.add(cursor.getString(0))
        }

        assertEquals(ingredients.contains("testIngredient"), true)

        db.removeIngredient("testIngredient")

        var cursor2 = db.getIngredient()
        var ingredients2 = mutableListOf<String>()

        while(cursor2!!.moveToNext()){
            ingredients2.add(cursor2.getString(0))
        }

        assertEquals(ingredients2.contains("testIngredient"), false)
    }

}

