package com.example.myapplication.presentation.ingredients

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R


class EnterIngredientDialogFragment : DialogFragment() {

    interface DialogFragmentListener{
        fun addValue(value:String)
    }
    private var listener: DialogFragmentListener? = null

    lateinit var closeButton: Button
    lateinit var searchView: SearchView
    lateinit var listView:ListView
    lateinit var addButton: Button
    lateinit var item: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_enter_ingredient_dialog, container, false)

        closeButton = view.findViewById(R.id.closeButton)
        searchView=view.findViewById(R.id.searchView)
        listView=view.findViewById(R.id.listView)
        addButton=view.findViewById(R.id.addButton)

        val list= arrayOf("garlic","whole chicken", "tomato","ginger","spring onion",
            "onion","eggs","butter","celery","corn","bacon","milk","salmon","sausage","carrot",
            "chicken breast","chicken wing","chicken drumstick","shrimp","pork ribs","ground beef"
            ,"ground pork","lamb rack","mushroom","pineapple","whipping cream","mozzarella"
            ,"cheddar","parmesan","lemon","lime","lettuce","thyme","rosemary","parsley","basil"
           ,"brussels sprout","broccoli","cauliflower","eggplant","cucumber","zucchini"
           ,"spinach","strawberry","orange","asparagus","green cabbage","red chilli","red pepper"
           ,"green pepper","pork belly","pork loin","beef tenderloin","steak","daikon radish")

        val arrayAdapter: ArrayAdapter<*>

        arrayAdapter = ArrayAdapter(
            activity as Context,
            android.R.layout.simple_list_item_1, list
        )

        listView.adapter = arrayAdapter

        closeButton.setOnClickListener {
            dismiss()
        }


        addButton.setOnClickListener {
            listener?.let {
                it.addValue(item)
            }
            //clear the search bar
            searchView.setQuery("", false)
            searchView.clearFocus()
            dismiss()
        }


        //to search for ingredients
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if(list.contains(query)){
                    arrayAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                arrayAdapter.filter.filter(newText)
                return false
            }

        })

        listView.setOnItemClickListener { parent, view, position, id ->
            item =parent.getItemAtPosition(position) as String
            Log.d("Hail","$item")
            searchView.setQuery(item, false)
            searchView.clearFocus()

        }

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnterIngredientDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param: DialogFragmentListener): EnterIngredientDialogFragment {
            val fragment = EnterIngredientDialogFragment().apply {
                listener = param
            }
            return fragment
        }
    }
}