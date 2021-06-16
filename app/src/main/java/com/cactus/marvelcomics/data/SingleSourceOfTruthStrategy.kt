package com.cactus.marvelcomics.data

import androidx.lifecycle.*
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
fun <T> resultLiveData(
    databaseQuery: (() -> LiveData<T>)?,
    networkCall: suspend () -> OperationResult,
    saveCallResult: (suspend (OperationResult?) -> Unit)? ): LiveData<OperationResult> =

    liveData(Dispatchers.IO) {
        emit(OperationResult.Loading)
        emit(networkCall.invoke())
    }