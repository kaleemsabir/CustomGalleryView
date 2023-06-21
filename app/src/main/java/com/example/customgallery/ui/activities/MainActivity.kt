package com.example.customgallery.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.customgallery.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.addOnBackPressedCallback(onBackPressedCallback)
    }

    private fun addOnBackPressedCallback(onBackPressedCallback: OnBackPressedCallback) {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOnBackPressedCallback(onBackPressedCallback)
    }

    private fun removeOnBackPressedCallback(onBackPressedCallback: OnBackPressedCallback) {
        onBackPressedCallback.remove()
    }
}