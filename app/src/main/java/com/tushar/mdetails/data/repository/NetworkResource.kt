package com.tushar.mdetails.data.repository

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkResource<T> {

    fun asFlow() = flow<Resource<T>> {

        // Emit Loading State
        emit(Resource.loading(null))

        try {
            // Fetch latest data from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val body = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && body != null) {
                emit(Resource.success(body))
            } else {
                // Something went wrong! Emit Error state with message.
                emit(Resource.error(apiResponse.message(), null))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(Resource.error("Network error! Can't get latest data.", null))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
}