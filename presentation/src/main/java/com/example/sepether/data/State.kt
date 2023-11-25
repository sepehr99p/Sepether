package com.example.sepether.data

data class DataState<T>(
    val data : T?,
    val isLoading : Boolean
)