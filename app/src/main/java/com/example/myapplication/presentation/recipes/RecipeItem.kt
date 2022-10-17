package com.example.myapplication.presentation.recipes

import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.Recipe
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.recipe_layout.view.*

class RecipeItem(private val recipe: Recipe) : Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            recipe_item_title.text = recipe.name
            recipe_item_difficulty.text = recipe.difficulty
            recipe_item_time.text = recipe.time

            Glide.with(context).load(recipe.image).into(recipe_item_image)
            when(recipe.difficulty) {
                "Easy", "Facil", "简单" -> recipe_item_difficulty.setTextColor(Color.rgb(0, 102, 0))
                "Medium", "Medio", "中等" -> recipe_item_difficulty.setTextColor(Color.rgb(255, 165, 0))
                "Hard", "Dificil","难" -> recipe_item_difficulty.setTextColor(Color.RED)
            }

            card_view.setOnClickListener {
                Log.d("intent", "click")
                var intent = Intent(viewHolder.itemView.context, RecipeDescriptionActivity::class.java)
                    .putExtra("name", recipe.name)
                    .putExtra("time", recipe.time)
                    .putExtra("difficulty", recipe.difficulty)
                    .putExtra("ingredients", recipe.ingredients)
                    .putExtra("steps", recipe.steps)
                    .putExtra("image", recipe.image)

                viewHolder.itemView.context.startActivity(intent)
            }
        }

    }

    override fun getLayout() = R.layout.recipe_layout
}