package com.tushar.mdetails.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class adds an api key to the url
 * for eg: https://url.com?api_key=jbhdjsvdjh12e12ec
 */
class ApiKeyInterceptor : Interceptor {

    companion object{
        const val API_KEY = "api_key"
        const val KEY = "2cdf3a5c7cf412421485f89ace91e373"
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