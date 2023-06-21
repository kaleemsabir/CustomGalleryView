package com.example.customgallery.ui.fragments.galleryfolderview.viewmodel

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customgallery.repos.GalleryFolderRepoImp
import com.example.customgallery.utils.Response
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
    private val _folderMediaList by lazy { MutableStateFlow(emptyList<FolderMedia>()) }
    private val _isLoading by lazy { MutableStateFlow(false) }
    val folderMediaList by lazy { _folderMediaList.asStateFlow() }
    val isLoading by lazy { _isLoading.asStateFlow() }
    private val _isLinearState = MutableStateFlow(false)
    val isLinearStat by lazy { _isLinearState.asStateFlow() }
    fun fetchAllGalleryFolders(contentResolverProvider: () -> ContentResolver) {
        viewModelScope.launch {
            repository.loadMediaFromGallery(
                contentResolver = contentResolverProvider(),
            ).collectLatest { dataState ->
                when (dataState) {
                    is Response.Loading -> {_isLoading.value = true}
                    is Response.Success -> {
                        dataState.data.let { data ->
                            _folderMediaList.value = data.sortedBy {folder-> folder.folderName }
                        }
                        _isLoading.value = false

                    }
                    is Response.Failure -> {
                        _isLoading.value = false
                    }

                }
            }
        }
    }
    fun changeState(state:Boolean){
        viewModelScope.launch {
            _isLinearState.value = state
        }
    }

}