package com.example.customgallery.ui.toolbar

import androidx.lifecycle.MutableLiveData


class ToolbarViewModel (val onBackPressedClick: () -> Unit) {

    var title = ""
    var isBackArrowShow = false
    var linearView = MutableLiveData(false)
    fun backArrowClick(){
        onBackPressedClick()
    }
}