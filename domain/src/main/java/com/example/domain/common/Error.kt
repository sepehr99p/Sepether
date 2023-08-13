package com.example.domain.common

import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

fun <T> checkError(throwable: Throwable): Resource.Error<T> {
    return when (throwable) {
        is UnknownHostException -> {
            Resource.Error(Const.NO_INTERNET)
        }
        is SSLHandshakeException -> {
            Resource.Error(Const.SSL_HANDSHAKE)
        }
        is SocketTimeoutException -> {
            Resource.Error(throwable.localizedMessage)
        }
        is ProtocolException -> {
            Resource.Error(throwable.localizedMessage)
        }
        else -> {
            Resource.Error(throwable.localizedMessage)
        }
    }
}
