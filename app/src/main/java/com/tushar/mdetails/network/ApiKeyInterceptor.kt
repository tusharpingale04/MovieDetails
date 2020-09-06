package com.tushar.mdetails.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    companion object{
        const val API_KEY = "api_key"
        const val KEY = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain
            .request()
            .url()
            .newBuilder()
            .addQueryParameter(API_KEY, KEY)
            .build()
        return chain.proceed(chain.request().newBuilder().url(url).build())
    }
}