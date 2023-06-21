package com.example.customgallery.ui.fragments.galleryMediaDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customgallery.databinding.MediaItemBinding
import com.example.customgallery.ui.fragments.galleryMediaDetail.viewmodel.GalleryMediaViewModel
import com.example.customgallery.ui.fragments.galleryfolderview.adapter.GalleryItemViewModel
import com.gallerydemo.data.local.models.MediaItem
import javax.inject.Inject

class MediaViewAdapter @Inject constructor() :
    RecyclerView.Adapter<MediaViewAdapter.MediaItemViewHolder>() {

    private var dataList: List<MediaItem> = mutableListOf()

    fun setDataList(data: List<MediaItem>) {
        this.dataList = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaViewAdapter.MediaItemViewHolder {
        return createGridViewItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun createGridViewItemViewHolder(parent: ViewGroup): MediaItemViewHolder {
        val itemBinding = MediaItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MediaItemViewHolder(itemBinding)
    }

    inner class MediaItemViewHolder(private val itemMediaBinding: MediaItemBinding) :
        RecyclerView.ViewHolder(itemMediaBinding.root) {
        fun bind(position: Int) {
            val viewModel = MediaItemViewModel(dataList[position])
            itemMediaBinding.viewModel = viewModel
        }
    }


}