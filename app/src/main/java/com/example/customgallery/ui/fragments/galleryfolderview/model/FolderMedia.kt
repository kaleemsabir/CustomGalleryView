package com.gallerydemo.data.local.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FolderMedia( val folderName: String, val mediaListItem: MutableList<MediaItem>) :
    Parcelable