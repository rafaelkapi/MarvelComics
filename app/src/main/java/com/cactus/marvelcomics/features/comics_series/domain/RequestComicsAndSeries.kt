package com.cactus.marvelcomics.features.comics_series.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.network.model.*
import com.cactus.marvelcomics.features.comics_series.data.ComicsSeriesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.reflect.KClass

interface RequestComicsAndSeries {
    suspend fun getComicListLiveData(list: List<BaseSummary>): List<Comic>
    suspend fun getSerieListLiveData(list: List<BaseSummary>): List<Serie>
}


class RequestComicsAndSeriesImp @Inject constructor(
    private val repository: ComicsSeriesRepository
) : RequestComicsAndSeries {


    override suspend fun getComicListLiveData(list: List<BaseSummary>): List<Comic> {
        val listAux = mutableListOf<Comic>()

        list.forEach {
            val result = repository.getComic(regexFindId(it.resourceURI ?: ""))
            listAux.addAll(loadList(Comic::class, result))
        }

        return listAux
    }


    override suspend fun getSerieListLiveData(list: List<BaseSummary>): List<Serie> {
        val listAux = mutableListOf<Serie>()

        list.forEach {
            val result = repository.getSerie(regexFindId(it.resourceURI ?: ""))
            listAux.addAll(loadList(Serie::class, result))
        }

        return listAux
    }


    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> loadList(clazz: KClass<T>, result: OperationResult): List<T> {

        val listOut = mutableListOf<T>()

        when (result) {
            is OperationResult.Success<*> -> {

                val dataWrapper = result.data
                when (clazz) {
                    Comic::class -> {
                        dataWrapper as ComicDataWrapper
                        listOut.addAll((dataWrapper.data?.results!!) as List<T>)
                    }
                    Serie::class -> {
                        dataWrapper as SerieDataWrapper
                        listOut.addAll((dataWrapper.data?.results!!) as List<T>)
                    }
                }
            }
            is OperationResult.Error -> {
                Log.d("Test loadComicList", "Error: ${result.error}")
            }
        }
        return listOut
    }


    private fun regexFindId(text: String): Int {
        val pattern = "(/)(\\d+)".toRegex()
        val found = pattern.find(text)
        var id = ""
        if (found != null) {
            id = found.groupValues[2]
        }
        return id.toInt()
    }


}

