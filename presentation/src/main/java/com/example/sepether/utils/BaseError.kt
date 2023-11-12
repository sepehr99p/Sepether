package com.example.sepether.utils

import android.content.Context
import com.example.sepether.R

sealed class BaseError {

    object Network : BaseError()
    object NotFound : BaseError()
    object AccessDenied : BaseError()
    object ServiceUnavailable : BaseError()
    object Canceled : BaseError()

    object NoLocationProvided : BaseError()

    data class Unknown(val message: String?) : BaseError()


    object InvalidInput : BaseError()
    object InvalidAuth:  BaseError()
    object ServerError: BaseError()
    object TooManyRequest: BaseError()
    object CodeIsUsed: BaseError()
    object CodeIsInvalid: BaseError()
    object CodeIsExpired: BaseError()
    object NoteAllowed: BaseError()

    fun toErrorMessage(context: Context): String {
        return when (this) {
            AccessDenied -> context.getString(R.string.accessDenied)
            Canceled -> context.getString(R.string.requestCanceled)
            Network -> context.getString(R.string.networkError)
            NotFound -> context.getString(R.string.notFound)
            ServiceUnavailable -> context.getString(R.string.serviceUnavailable)

            InvalidInput -> context.getString(R.string.invalidInput)
            NoLocationProvided -> context.getString(R.string.noLocationProvided)
            InvalidAuth -> context.getString(R.string.invalid_auth)
            ServerError -> context.getString(R.string.serverError)
            TooManyRequest -> context.getString(R.string.tooManyRequest)
            CodeIsExpired -> context.getString(R.string.codeIsExpired)
            CodeIsInvalid -> context.getString(R.string.codeIsInvalid)
            CodeIsUsed -> context.getString(R.string.codeIsUsed)
            NoteAllowed -> context.getString(R.string.notAllowed)

            is Unknown -> message ?: context.getString(R.string.unknownError)
            else -> "Unknown"
        }
    }
}
