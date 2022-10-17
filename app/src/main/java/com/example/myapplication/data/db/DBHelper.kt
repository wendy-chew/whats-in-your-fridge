package com.example.myapplication.data.db

import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.sqlite.transaction

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                INGREDIENT_COL + " TEXT"+ ")")

        //  calling sqlite method to execute the query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data to database
    fun addIngredient(ingredient: String ){
        Log.d("add ing", ingredient)
        // a content values variable
        val values = ContentValues()

        // inserting  values in the form of key-value pair
        values.put(INGREDIENT_COL, ingredient)


        // writable variable of database to insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // close database
        db.close()
    }

    fun deleteAll(){
        val db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME)
        db.close()
    }

    // to retrieve all ingredients from database
    fun getIngredient(): Cursor? {
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // returns a cursor to read data from the database
        return db.rawQuery(
            "SELECT $INGREDIENT_COL FROM $TABLE_NAME"
            , null)
    }

    fun removeIngredient(value: String) {

        val db = this.getWritableDatabase()
        db.delete(TABLE_NAME, INGREDIENT_COL + "= '" + value + "'", null) > 0
        //db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+ INGREDIENT_COL +"='"+value+"'")
        db.close()
    }

    companion object{
        // define variables for database

        //  database name
        private val DATABASE_NAME = "cs412_final_project"

        // database version
        private val DATABASE_VERSION = 1

        // table name
        val TABLE_NAME = "Ingredient"

        // variable for id column
        val ID_COL = "id"

        // variable for question column
        val INGREDIENT_COL = "ingredient"

    }
}