package com.example.customgallery.utils

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure(val error: String? = null) : Response<Nothing>()
    object Loading : Response<Nothing>()
}