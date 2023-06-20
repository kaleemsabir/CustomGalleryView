package com.gallerydemo.data.local.models

data class MediaItem(
    val mediaPath:String,
    val id: Int,
    val mimeType: String
) {
    val isVideo: Boolean = mimeType.startsWith("video", true)
}