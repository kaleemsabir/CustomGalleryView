package com.example.customgallery.ui.fragments.galleryfolderview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customgallery.databinding.GalleryFolderItemBinding
import com.example.customgallery.databinding.LinearViewGalleryFolderItemBinding
import javax.inject.Inject

class GalleryFolderViewAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLinearView = false
    private  var list = mutableListOf<Any>()
    companion object {
        private const val ITEM_GRID_VIEW = 0
        private const val ITEM_LINEAR_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_GRID_VIEW -> createGridViewItemViewHolder(parent)
            else -> createLinearViewItemViewHolder(parent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isLinearView) {
            ITEM_LINEAR_VIEW
        } else {
            ITEM_GRID_VIEW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isLinearView) {
            (holder as LinearViewHolderViewHolder).bind(position)
        } else {
            (holder as GridViewHolderViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

    private fun createGridViewItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = GalleryFolderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GridViewHolderViewHolder(itemBinding)
    }

    private inner class GridViewHolderViewHolder(itemGalleryFolder: GalleryFolderItemBinding) :
        RecyclerView.ViewHolder(itemGalleryFolder.root) {
        fun bind(position: Int) {
        }
    }

    private fun createLinearViewItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = LinearViewGalleryFolderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LinearViewHolderViewHolder(itemBinding)
    }

    private inner class LinearViewHolderViewHolder(itemLinearGalleryFolder: LinearViewGalleryFolderItemBinding) :
        RecyclerView.ViewHolder(itemLinearGalleryFolder.root) {
        fun bind(position: Int) {
        }
    }

}