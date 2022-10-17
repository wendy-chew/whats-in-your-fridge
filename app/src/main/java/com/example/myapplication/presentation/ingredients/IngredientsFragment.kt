package com.example.myapplication.presentation.ingredients

import android.os.Bundle

import android.util.Log

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.ingredients.adapters.IngredientAdapter
import com.example.myapplication.data.db.DBHelper
import com.example.myapplication.data.model.Ingredient
import com.example.myapplication.presentation.ingredients.`interface`.RecyclerViewInterface
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import kotlin.concurrent.thread


class IngredientsFragment : Fragment(), EnterIngredientDialogFragment.DialogFragmentListener,
    RecyclerViewInterface {

    private var recyclerView:RecyclerView ?= null
    private var arrayList:ArrayList<Ingredient>?=null
    private var ingredientAdapter:IngredientAdapter?=null
    var ingredientList:ArrayList<Ingredient> = ArrayList()
    var dialog= EnterIngredientDialogFragment.newInstance(this)
    lateinit var db : DBHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Retrieve the ingredients from the database and
    //add them to the ingredientList
    private fun setDataInList(): ArrayList<Ingredient> {
            val cursor = db.getIngredient()
            //iterate over table of results and add the ingredient to ingredientList
            while (cursor!!.moveToNext()){
                val ingredient = cursor.getString(0)
                val image = requireContext().resources.getIdentifier(ingredient.replace("\\s".toRegex(), ""),
                    "drawable", requireContext().packageName)
                ingredientList.add(Ingredient(image,ingredient))

            }
            // close cursor
            cursor.close()

            Log.d("Hail", "Ingredient in the database : $ingredientList")

        return ingredientList
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        db=DBHelper(requireContext(),null)

        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_ingredients, container, false)
        recyclerView=view.recycler_view
        recyclerView?.layoutManager= GridLayoutManager(requireContext(),3
        ,LinearLayoutManager.VERTICAL ,false)
        recyclerView?.setHasFixedSize(true)
        //arrayList = ArrayList()
        arrayList = setDataInList()
        ingredientAdapter= IngredientAdapter(requireContext(), arrayList!!, this)
        recyclerView?.adapter=ingredientAdapter



        view.fab.setOnClickListener {
            Log.d("Hail","fab clicked")
            dialog.show(childFragmentManager, "dialog")
        }


        return view
    }



    //Add the ingredient to the database and
    //add to the ingredientList one by one
    override fun addValue(value: String) {
        //check if the ingredient has already been added
        var added = false
        val ingredientToBeAdded = value
        for (ingredient in ingredientList) {
            if (ingredient.text == value) {
                added = true
                break
            }
        }
        if (added) {
            Toast.makeText(requireContext(), "$ingredientToBeAdded has already been added", Toast.LENGTH_LONG).show()
        }

        else {
            //add the ingredient to the database
            thread {
                db.addIngredient(value)
            }


                Log.d("Hail", "$value added to Database")


                //get the drawable image
                val image = requireContext().resources.getIdentifier(
                    value.replace("\\s".toRegex(), ""),
                    "drawable", requireContext().packageName
                )

                //add the ingredient to the Ingredient list
                ingredientList.add(Ingredient(image, value))
                //notify the adapter
                ingredientAdapter?.notifyDataSetChanged()
                recyclerView?.scrollToPosition(ingredientList.size - 1)

        }
    }

    override fun deleteValueOnLongClick(position: Int, value: String?) {
        // remove the ingredient from  the database
        if (value != null) {
            thread {
                db.removeIngredient(value)
            }
            Log.d("hail","ingredient: $value, removed from database")
        }

        ingredientList.removeAt(position)
        Log.d("Hail","$ingredientList")
        ingredientAdapter?.notifyItemRemoved(position)






    }

}