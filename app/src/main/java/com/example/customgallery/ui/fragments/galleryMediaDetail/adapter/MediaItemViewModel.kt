package com.example.customgallery.ui.fragments.galleryMediaDetail.adapter

import com.gallerydemo.data.local.models.FolderMedia
import com.gallerydemo.data.local.models.MediaItem


class MediaItemViewModel(media: MediaItem) {
    val isVideo: Boolean = media.isVideo
    val mediaImageUrl: String = media.mediaPath
}