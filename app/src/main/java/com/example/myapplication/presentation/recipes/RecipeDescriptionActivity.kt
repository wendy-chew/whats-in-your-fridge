package com.example.myapplication.presentation.recipes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.db.DBHelper
import com.example.myapplication.presentation.main.MainActivity


class RecipeDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_description)

        var db = DBHelper(this,null)

        val cursor = db.getIngredient()
        var ingredients = mutableListOf<String>()

        cursor!!.moveToFirst()

        while (cursor!!.moveToNext()){
            ingredients.add(cursor.getString(0))
            Log.d("ing", cursor.getString(0))
        }


        //Getting ids
        val recipeImage = findViewById<ImageView>(R.id.recipeImage)
        val recipeDifficulty = findViewById<TextView>(R.id.difficulty)
        val recipeTime = findViewById<TextView>(R.id.time)
        val recipeInstruction = findViewById<TextView>(R.id.instructions)
        val recipeIngredients = findViewById<LinearLayout>(R.id.recipeIngredients)



        //Getting extras from activity
        val recipeName = intent.getStringExtra("name")
        val recipeTimeR = intent.getStringExtra("time")
        val recipeDifficultyR = intent.getStringExtra("difficulty")
        val recipeSteps = intent.getStringArrayExtra("steps")
        val recipeIngredientsR = intent.getStringArrayExtra("ingredients")
        val image = intent.getStringExtra("image")
        val recipeStepsSize = recipeSteps?.size
        var stepsCounter = 0

        while (stepsCounter != recipeStepsSize){
            recipeInstruction.text = recipeInstruction.text as String? + "\n" +recipeSteps?.get(stepsCounter)
            stepsCounter += 1
        }


        if (recipeIngredientsR != null) {
            for (ing in recipeIngredientsR) {
                var textview = TextView(this)
                textview.text = ing
                recipeIngredients.addView(textview)
            }
        }


        title = recipeName
        recipeDifficulty.text = recipeDifficultyR
        recipeTime.text = recipeTimeR

        Glide.with(this).load(image).into(recipeImage)


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).putExtra("cameFromRecipeDetails", true)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent =
                Intent(this, MainActivity::class.java).putExtra("cameFromRecipeDetails", true)
            startActivity(intent)
            finish()

        }
        return true
    }


}