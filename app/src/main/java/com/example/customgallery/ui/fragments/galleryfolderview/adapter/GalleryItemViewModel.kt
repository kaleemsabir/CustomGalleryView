package com.example.customgallery.ui.fragments.galleryfolderview.adapter

import com.gallerydemo.data.local.models.FolderMedia


class GalleryItemViewModel(folder: FolderMedia ,val  onItemClick :()->Unit ) {
    val folderName: String = folder.folderName
    val folderCount: String = folder.mediaListItem.size.toString()
    val folderImageUrl: String? = folder.mediaListItem.firstOrNull()?.mediaPath

   fun clickOnItem(){
       onItemClick()
   }
}