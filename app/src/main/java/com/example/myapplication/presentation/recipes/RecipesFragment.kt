package com.example.myapplication.presentation.recipes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.data.model.Recipe
import com.example.myapplication.data.api.RecipesApi
import com.example.myapplication.data.api.RetrofitHelper
import com.example.myapplication.data.db.DBHelper
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class RecipesFragment : Fragment() {


    lateinit var api: RecipesApi
    lateinit var call: Call<List<Recipe>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        var adapter = GroupAdapter<GroupieViewHolder>()

        api = RetrofitHelper.getRecipesApi(requireContext())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            call = api.getRecipes(requireContext().resources.configuration.locales.get(0).language)
        } else {
            call = api.getRecipes(requireContext().resources.configuration.locale.language)
        }



        call.enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                progressBar?.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    var db = DBHelper(requireContext(), null)
                    var ingredients = mutableListOf<String>()

                    val cursor = db.getIngredient()
                    while (cursor!!.moveToNext()) {
                        ingredients.add(cursor.getString(0))
                    }

                    for (recipe in response.body()!!) {
                        if (recipe.hasIngredients(ingredients)) {
                            adapter.add(RecipeItem(recipe))
                        }
                    }

                    if(adapter.itemCount == 0)
                        no_recipesTV.visibility = View.VISIBLE


                }

                else
                    Toast.makeText(
                        context,
                        "Something went wrong. Please try again",
                        Toast.LENGTH_LONG
                    ).show()
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                if (!call.isCanceled) {
                    Log.d("retrofit", t.message!!)
                    Toast.makeText(
                        context,
                        "Something went wrong. Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar?.visibility = View.INVISIBLE
                }
            }
        })


        recipes_recycler_view.adapter = adapter


    }

    override fun onDestroy() {
        super.onDestroy()
        call.cancel()

    }


}





