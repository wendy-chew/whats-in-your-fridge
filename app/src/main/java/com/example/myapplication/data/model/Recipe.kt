package com.example.myapplication.data.model

import android.util.Log

data class Recipe(val id: Int, val name: String, val image: String, val time: String, val difficulty: String,
    val ingredients : Array<String>, val steps : Array<String>) {




    fun hasIngredients(myIngredients: List<String>) : Boolean {
        Log.d("ingredients", myIngredients.size.toString())
        for(ing in ingredients) {
            if(!myIngredients.contains(ing))
                return false
        }

        return true
    }
}