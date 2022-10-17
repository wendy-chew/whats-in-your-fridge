package com.example.myapplication.presentation.gallery

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.layout_gallery_item.view.*
import kotlinx.android.synthetic.main.recipe_layout.view.*

class GalleryImageItem(val imageUri: Uri, val adapterListener: AdapterListener) : Item<GroupieViewHolder>() {


    override fun getLayout() = R.layout.layout_gallery_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context).load(imageUri).placeholder(ColorDrawable()).into(viewHolder.itemView.gallery_item_imageview)

        viewHolder.itemView.setOnLongClickListener {
            adapterListener.onClickItem(position, it, imageUri)
            return@setOnLongClickListener true
        }
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int  = 1


    interface AdapterListener {
        fun onClickItem(position: Int, view: View, imageUri: Uri)
    }


}