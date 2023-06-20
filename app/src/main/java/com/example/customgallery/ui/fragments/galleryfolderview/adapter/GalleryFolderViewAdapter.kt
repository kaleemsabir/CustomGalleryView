package com.example.customgallery.ui.fragments.galleryfolderview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customgallery.databinding.GalleryFolderItemBinding
import com.example.customgallery.databinding.LinearViewGalleryFolderItemBinding
import com.gallerydemo.data.local.models.FolderMedia
import javax.inject.Inject

class GalleryFolderViewAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLinearView = false
    companion object {
        private const val ITEM_GRID_VIEW = 0
        private const val ITEM_LINEAR_VIEW = 1
    }
    private var dataList: List<FolderMedia> = mutableListOf()
    private lateinit var galleryFolderClickListener : GalleryFolderClickListener
     fun setDataList(data: List<FolderMedia>){
        this.dataList = data
        notifyDataSetChanged()
    }
    fun setLinearState(isLinearView : Boolean){
        this.isLinearView = isLinearView
        notifyDataSetChanged()
    }

    fun setClickListener(galleryFolderClickListener : GalleryFolderClickListener){
        this.galleryFolderClickListener = galleryFolderClickListener
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
      return dataList.size
    }

    private fun createGridViewItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = GalleryFolderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GridViewHolderViewHolder(itemBinding)
    }

    private inner class GridViewHolderViewHolder(val itemGalleryFolder: GalleryFolderItemBinding) :
        RecyclerView.ViewHolder(itemGalleryFolder.root) {
        fun bind(position: Int) {
            val viewModel = GalleryItemViewModel(dataList[position]) {
                galleryFolderClickListener.onItemClick(dataList[position])
            }
            itemGalleryFolder.viewModel = viewModel
        }
    }

    private fun createLinearViewItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = LinearViewGalleryFolderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LinearViewHolderViewHolder(itemBinding)
    }

    private inner class LinearViewHolderViewHolder(val itemLinearGalleryFolder: LinearViewGalleryFolderItemBinding) :
        RecyclerView.ViewHolder(itemLinearGalleryFolder.root) {
        fun bind(position: Int) {
            val viewModel = GalleryItemViewModel(dataList[position]) {
                galleryFolderClickListener.onItemClick(dataList[position])
            }
            itemLinearGalleryFolder.viewModel = viewModel
        }
    }

    interface GalleryFolderClickListener{
        fun onItemClick(foldermedia : FolderMedia)
    }

}