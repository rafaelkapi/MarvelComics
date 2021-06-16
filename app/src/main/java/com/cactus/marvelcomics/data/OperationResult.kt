package com.cactus.marvelcomics.data

sealed class OperationResult  {
    class Success<T>(val data: T) : OperationResult()
    object Loading : OperationResult()
    class Error(val error: String?) : OperationResult()
 }


