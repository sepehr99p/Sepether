package com.example.domain.common

import retrofit2.Response

fun <T> checkResponse(response: Response<T>): Resource<T> {
    return if (response.isSuccessful) {
        response.body()?.let {
            Resource.Success(it)
        } ?: run {
            Resource.Error(Const.EMPTY_BODY_ERROR)
        }
    } else {
        if (response.code() == 401){
            response.errorBody()?.let {
                if (it.toString().contains("Token is not valid")){
                    Resource.Error("Invalid Token",null)
                }
            }
        }
        if (response.code().toString().startsWith("5")) {
            Resource.Error(Const.SERVER_ERROR)
        } else {
            Resource.Error(response.errorBody()?.string() ?: Const.DEFAULT_ERROR)
        }
    }
}
