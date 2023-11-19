package com.example.data.common

import com.example.data.mapper.MapperCallback
import com.example.domain.common.Const
import com.example.domain.common.Resource
import retrofit2.Response

fun <F,T> checkResponse(response: Response<F>,mapperCallback: MapperCallback<F,T>): Resource<T> {
    return if (response.isSuccessful) {
        response.body()?.let {
            Resource.Success(mapperCallback.map(it))
        } ?: run {
            Resource.Error(Const.EMPTY_BODY_ERROR)
        }
    } else {
        if (response.code() == 401){
            response.errorBody()?.let {
                if (it.toString().contains("Token is not valid")){
                    Resource.Error("Invalid Token", null)
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
