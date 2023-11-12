package com.example.sepether.ui.components

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import com.example.domain.common.Resource
import com.example.sepether.utils.BaseError
import com.google.android.gms.common.api.Response



typealias OnSuccessCallback<T> = @Composable (T?) -> Unit
typealias OnFailedCallback = @Composable (BaseError) -> Unit

@LayoutScopeMarker
class CallbackStack<T> {

    var isStarted = false

    var onSuccess: (OnSuccessCallback<T>)? = null
    var onFailed: (OnFailedCallback)? = null
    var doFinally: (@Composable () -> Unit)? = null
    var onLoading: (@Composable () -> Unit)? = null

    @Composable
    fun onSuccess(cb: OnSuccessCallback<T>) {
        this.onSuccess = cb
    }

    @Composable
    fun onFailed(cb: OnFailedCallback) {
        this.onFailed = cb
    }

    @Composable
    fun onLoading(cb: @Composable () -> Unit) {
        this.onLoading = cb
    }

    @Composable
    fun doFinally(cb: @Composable () -> Unit) {
        this.doFinally = cb
    }
}


@Composable
inline fun <T> State<Resource<T>?>.callback(crossinline action: @Composable CallbackStack<T>.() -> Unit) {
    val stack = remember(this) { CallbackStack<T>() }
    stack.action()
    this.value?.let { data->
        when (data) {
            is Resource.Loading -> {
                stack.isStarted = true
                stack.onLoading?.invoke()
            }
            is Resource.Success<T> -> {
                stack.onSuccess?.invoke(data.data)
            }
            is Resource.Error -> {
//                stack.onFailed?.invoke(data.error)
            }
        }

        if (data !is Resource.Loading && stack.isStarted) {
            stack.isStarted = false
            stack.doFinally?.invoke()
        }
    }
}
