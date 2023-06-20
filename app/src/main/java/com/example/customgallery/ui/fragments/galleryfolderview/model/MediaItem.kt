package com.gallerydemo.data.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaItem(
    val mediaPath:String,
    val id: Int,
    val mimeType: String
) : Parcelable {
    val isVideo: Boolean = mimeType.startsWith("video", true)
}