package com.cactus.marvelcomics.data

import androidx.lifecycle.*
import com.cactus.marvelcomics.data.network.model.DataWrapper
import kotlinx.coroutines.Dispatchers
import java.lang.reflect.Array.get

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun resultLiveData(
    databaseQuery: (() -> LiveData<OperationResult>)?,
    networkCall: suspend () -> OperationResult,
    saveCallResult: (suspend (OperationResult?) -> Unit)?
): LiveData<OperationResult> =

    liveData(Dispatchers.IO) {
        emit(OperationResult.Loading)

        networkCall.invoke().let { response ->
            when (response) {
                is OperationResult.Success<*> -> {
                    val dataWrapper = response.data as DataWrapper
                    saveCallResult?.invoke(OperationResult.Success(dataWrapper.data?.results))
                    // No Local Data Source
                    emit(OperationResult.Success(dataWrapper.data?.results))
                }
                is OperationResult.Error -> {
                    emit(response)
                }
            }
        }
    }