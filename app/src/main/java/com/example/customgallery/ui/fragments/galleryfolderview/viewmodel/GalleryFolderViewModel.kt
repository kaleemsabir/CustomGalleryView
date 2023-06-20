package com.example.customgallery.ui.fragments.galleryfolderview.viewmodel

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customgallery.repos.GalleryFolderRepoImp
import com.example.customgallery.utils.UiState
import com.gallerydemo.data.local.models.FolderMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryFolderViewModel @Inject constructor(private val repository: GalleryFolderRepoImp) :
    ViewModel() {
    private val _folderMediaList = MutableStateFlow(emptyList<FolderMedia>())

    val folderMediaList = _folderMediaList.asStateFlow()

    fun fetchAllGalleryFolders(contentResolverProvider: () -> ContentResolver) {
        viewModelScope.launch {
            repository.loadMediaFromGallery(
                contentResolver = contentResolverProvider(),
            ).collectLatest { apiState ->
                when (apiState) {
                    is UiState.Loading -> {}
                    is UiState.Success -> {
                        apiState.data.let {
                            _folderMediaList.value = (it)
                        }

                    }
                    is UiState.Failure -> {
                    }
                }
            }
        }
    }

}