package com.gallerydemo.data.local.models

data class MediaItem(
    val id: Int,
    val mimeType: String
) {
    val isVideo: Boolean = mimeType.startsWith("video", true)
}