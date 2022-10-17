package com.example.myapplication.presentation.main

import androidx.appcompat.app.AppCompatActivity


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.presentation.gallery.GalleryFragment
import com.example.myapplication.presentation.ingredients.IngredientsFragment
import com.example.myapplication.presentation.recipes.RecipesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cameFromRecipeDetails = intent.extras?.get("cameFromRecipeDetails")
        Log.d("extras", intent.extras?.size().toString())

        if(cameFromRecipeDetails != null) {
            loadFragment(RecipesFragment())
            bottom_navigation.selectedItemId = R.id.recipes_menu_item
        }
        else
            loadFragment(IngredientsFragment())


        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ingredients_menu_item -> {
                    loadFragment(IngredientsFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.recipes_menu_item -> {
                    loadFragment(RecipesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.gallery_menu_item -> {
                    loadFragment(GalleryFragment())

                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }


            }

        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }



}

