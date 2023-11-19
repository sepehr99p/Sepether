package com.example.data.mapper

interface MapperCallback<From, To> {
    fun map(value: From): To
}