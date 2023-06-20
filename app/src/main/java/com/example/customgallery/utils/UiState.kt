package com.example.customgallery.utils

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val error: String? = null) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}