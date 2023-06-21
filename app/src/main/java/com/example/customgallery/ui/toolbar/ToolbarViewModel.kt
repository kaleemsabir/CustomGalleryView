package com.example.customgallery.ui.toolbar

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class ToolbarViewModel @Inject constructor() {

    var title = ""
    var isBackArrowShow = false
    var isSwitchStateShow = true
    var linearView = MutableLiveData(false)

}