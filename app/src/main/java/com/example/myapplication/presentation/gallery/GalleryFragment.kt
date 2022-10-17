package com.example.myapplication.presentation.gallery

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_gallery.*

import java.io.File


class GalleryFragment : Fragment(R.layout.fragment_gallery), GalleryImageItem.AdapterListener {

    private val FILE_PROVIDER = "com.example.fileprovider"

    private var uri : Uri? = null
    private lateinit var adapter: GroupAdapter<GroupieViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GroupAdapter<GroupieViewHolder>()
        val layoutManager = GridLayoutManager(context, 2)

        adapter.spanCount = 2

        loadImages()
        gallery_recyclerview.layoutManager = layoutManager
        gallery_recyclerview.adapter = adapter

        var resultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if(isSuccess) {
                adapter.add(GalleryImageItem(uri!!, this))
            }
            else {
                requireContext().deleteFile(uri?.lastPathSegment)
            }

        }
        gallery_fab.setOnClickListener {
            resultLauncher.launch(getTmpFileUri())
        }
    }


    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("image_file", ".png", requireContext().filesDir).apply {
            createNewFile()
        }
        var fileUri = getUriForFile(requireContext(), FILE_PROVIDER, tmpFile)
        uri = fileUri
        return fileUri

    }

     private fun loadImages() {

        requireContext().filesDir.listFiles().forEach { file ->
            adapter.add(GalleryImageItem(getUriForFile(requireContext(), FILE_PROVIDER, file), this))
        }

    }

    override fun onClickItem(position: Int, view: View, imageUri: Uri) {
        val popupMenu =
            PopupMenu(context, view)
        popupMenu.inflate(R.menu.show_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    requireContext().deleteFile(imageUri.lastPathSegment)
                    adapter.remove(adapter.getItem(position))
                    adapter.notifyItemRemoved(position)

                    true
                }
                else -> false

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.show()

    }


}