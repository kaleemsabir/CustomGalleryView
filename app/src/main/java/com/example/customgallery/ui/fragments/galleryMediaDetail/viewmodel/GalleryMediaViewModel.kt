package com.example.customgallery.ui.fragments.galleryMediaDetail.viewmodel

import androidx.lifecycle.ViewModel
import com.gallerydemo.data.local.models.FolderMedia
import com.gallerydemo.data.local.models.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryMediaViewModel @Inject constructor() : ViewModel() {
    lateinit var _mediaDataItem: FolderMedia
    fun getMediaItem() = _mediaDataItem
    fun setMediaItem(media: FolderMedia) {
        _mediaDataItem = media
    }

}