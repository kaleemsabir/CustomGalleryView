package com.example.customgallery.repos

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import com.example.customgallery.utils.IMAGE_AND_VIDEOS
import com.example.customgallery.utils.Response
import com.gallerydemo.data.local.models.FolderMedia
import com.gallerydemo.data.local.models.MediaItem
import kotlinx.coroutines.flow.Flow

interface GalleryFolderRepoInterface {
    fun loadMediaFromGallery(
        contentResolver: ContentResolver, galleryMode: Int = IMAGE_AND_VIDEOS,
    ): Flow<Response<List<FolderMedia>>>

    fun getGallerySelectionQuery(galleryMode: Int): String

    fun getProjectionArrayWithRequiredData() : Array<String>

    fun runWithContentResolver(contentResolver: ContentResolver, galleryMode: Int): List<FolderMedia>

    fun cursorToGalleryMedia(cursor: Cursor?): Pair<String, MediaItem>?

    fun getUriFromPathSegment(mediaId: Long): Uri

}