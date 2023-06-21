package com.example.customgallery.repos

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.customgallery.R
import com.example.customgallery.utils.IMAGE
import com.example.customgallery.utils.IMAGE_AND_VIDEOS
import com.example.customgallery.utils.Response
import com.example.customgallery.utils.VIDEO
import com.gallerydemo.data.local.models.FolderMedia
import com.gallerydemo.data.local.models.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GalleryFolderRepoImp @Inject constructor() : GalleryFolderRepoInterface {
    override fun loadMediaFromGallery(
        contentResolver: ContentResolver,
        context: Context,
        galleryMode: Int,
    ): Flow<Response<List<FolderMedia>>> {
        return flow {
            emit(Response.Loading)
            val data = runWithContentResolver(
                contentResolver = contentResolver,
                context = context,
                galleryMode = galleryMode
            )
            emit(Response.Success(data))
        }.flowOn(Dispatchers.IO)
    }

    override fun getGallerySelectionQuery(galleryMode: Int): String {
        return when (galleryMode) {
            IMAGE_AND_VIDEOS -> StringBuilder().append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                .append(" IN(")
                .append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                .append(",")
                .append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
                .append(")").toString()
            VIDEO -> StringBuilder().append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                .append("=")
                .append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).toString()
            else -> StringBuilder().append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                .append("=")
                .append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).toString()
        }
    }


    override fun runWithContentResolver(
        contentResolver: ContentResolver,
        context: Context,
        galleryMode: Int
    ): List<FolderMedia> {

        val folders = mutableListOf<FolderMedia>()
        val cursor: Cursor?
        val uri: Uri = MediaStore.Files.getContentUri("external")
        val selection: String = getGallerySelectionQuery(galleryMode)
        val projection = getProjectionArrayWithRequiredData()
        cursor = contentResolver.query(
            uri,
            projection,
            selection,
            null,
            null
        )
        if (cursor != null) {
            val foldersMap =
                mutableMapOf<String, FolderMedia>() // key is folderName here we can group media by folder names
            val allVideo: MutableList<MediaItem> by lazy { mutableListOf() }
            val allImages: MutableList<MediaItem> by lazy { mutableListOf() }
            val canAddImages =
                galleryMode != VIDEO // if not restricted to have videos only
            val canAddVideos =
                galleryMode != IMAGE // if not restricted to have images only
            while (cursor.moveToNext()) {
                cursorToGalleryMedia(cursor)?.let { (folderName, mediaItem) ->

                    if (canAddVideos && mediaItem.isVideo)
                        allVideo.add(mediaItem)
                    else if (canAddImages && !mediaItem.isVideo)
                        allImages.add(mediaItem)
                    foldersMap.getOrElse(folderName) {
                        foldersMap.put(
                            folderName,
                            FolderMedia(folderName, mutableListOf())
                        )
                    }?.mediaListItem?.add(mediaItem)

                }
            }
            if (canAddVideos)
                folders.add(
                    FolderMedia(
                        context.resources.getString(R.string.all_videos),
                        allVideo
                    )
                )
            folders.addAll(foldersMap.values)
            if (canAddImages)
                folders.add(
                    FolderMedia(
                        context.resources.getString(R.string.all_images),
                        allImages
                    )
                )
        }
        return folders

    }

    override fun getProjectionArrayWithRequiredData(): Array<String> {
        return arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.TITLE,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
    }

    override fun cursorToGalleryMedia(cursor: Cursor?): Pair<String, MediaItem>? {
        return cursor?.let {
            Pair(
                it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                MediaItem(
                    id = it.getInt(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                    mimeType = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)),
                    mediaPath = getUriFromPathSegment(
                        cursor.getLong(
                            cursor.getColumnIndexOrThrow(
                                MediaStore.Images.Media._ID
                            )
                        )
                    ).toString()
                )
            )
        }
    }

    override fun getUriFromPathSegment(mediaId: Long): Uri {
        return Uri.withAppendedPath(MediaStore.Files.getContentUri("external"), mediaId.toString())
    }
}