package com.example.myapplication.presentation.ingredients.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Ingredient
import com.example.myapplication.presentation.ingredients.`interface`.RecyclerViewInterface
import kotlinx.android.synthetic.main.grid_layout_list_item.view.*
import java.util.ArrayList



class IngredientAdapter(var context: Context,var arrayList: ArrayList<Ingredient>,
                        var recyclerViewInterface: RecyclerViewInterface)
    :RecyclerView.Adapter<IngredientAdapter.ItemHolder>(){




    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder =LayoutInflater.from(parent.context).
        inflate(R.layout.grid_layout_list_item,parent,false)

        return ItemHolder(itemHolder,recyclerViewInterface)
    }



    override fun getItemCount(): Int {
        return arrayList.size
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var ingredient: Ingredient =arrayList.get(position)
        ingredient.icon?.let {
            holder.icons.setImageResource(it)
        }

        ingredient.text?.let {
            holder.icons_text.text=it
        }


    }




    @RequiresApi(Build.VERSION_CODES.Q)
    class  ItemHolder(itemView: View, recyclerViewInterface: RecyclerViewInterface):
        RecyclerView.ViewHolder(itemView){
        var icons =itemView.icons
        var icons_text = itemView.icons_text



        init {

            //call deleteValueOnLongClick when the item is being long clicked
            itemView.setOnLongClickListener {

                var position = absoluteAdapterPosition
                val popupMenu = PopupMenu(itemView.context,it)
                popupMenu.inflate(R.menu.show_menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.delete->{
                            Log.d("Hail","Delete is clicked")
                            recyclerViewInterface?.let {
                                it.deleteValueOnLongClick(position,icons_text.text.toString())
                            }
                            true
                        }
                        else-> true
                    }
                }

                popupMenu.setForceShowIcon(true);

                popupMenu.show()

                return@setOnLongClickListener true
            }
        }

    }




}