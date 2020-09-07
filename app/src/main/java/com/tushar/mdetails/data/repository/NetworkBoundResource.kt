package com.tushar.mdetails.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.tushar.mdetails.network.ApiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * This Class helps to fetch the data from network and persist into DB and then
 * send it back to UI.
 */
abstract class NetworkBoundResource<ResultType,RequestType : NetworkResponseModel,
        Mapper : NetworkResponseMapper<RequestType>> {

    fun asFlow() = flow {
        emit(Resource.loading(null))
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(Resource.loading(dbValue))
            val apiResponse = fetchFromNetwork()
            if(apiResponse.isSuccessful && apiResponse.body() != null && apiResponse.code() == 200){

                saveNetworkResult(processResponse(apiResponse)!!)
                emitAll(loadFromDb().map { Resource.success(it) })
            }else{
                val apiError: ApiError = Gson().fromJson(apiResponse.errorBody()?.charStream(), ApiError::class.java)
                val errorMsg = if (!apiError.errorMessage.isNullOrEmpty()) {
                    apiError.errorMessage
                } else{
                    "Something Went Wrong! Please Try Again Later"
                }
                onFetchFailed(errorMsg)
                emitAll(loadFromDb().map {
                    Resource.error(errorMsg, it)
                })
            }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    @WorkerThread
    protected open fun processResponse(response: Response<RequestType>) = response.body()

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Response<RequestType>

    @MainThread
    protected abstract fun onFetchFailed(message: String?)

    @MainThread
    protected abstract fun mapper(): Mapper
}
