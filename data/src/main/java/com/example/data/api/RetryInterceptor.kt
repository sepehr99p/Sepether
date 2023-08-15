package com.example.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetries: Int = 3) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        var retryCount = 0
        val request = chain.request()

        while (retryCount < maxRetries && (response == null || !response.isSuccessful)) {
            try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                // Log the exception if needed
                retryCount++
                if (retryCount <= maxRetries) {
                    // Delay before the next retry (you can customize this)
                    Thread.sleep(1000 * retryCount.toLong())
                }
            }
        }

        return response ?: throw IOException("Maximum retries reached")
    }
}
