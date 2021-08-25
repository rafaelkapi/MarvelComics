package com.cactus.marvelcomics.features.comics_series.data

import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.db.AppDataBase
import com.cactus.marvelcomics.data.network.BaseDataSource
import com.cactus.marvelcomics.features.details.data.ComisSeriesService
import javax.inject.Inject

interface ComicsSeriesRepository {
    suspend fun getComic(id: Int): OperationResult
    suspend fun getSerie(id: Int): OperationResult
}

class ComicsSeriesRepositoryImp @Inject constructor(
    private val service: ComisSeriesService,
) : BaseDataSource(), ComicsSeriesRepository {

    override suspend fun getComic(id: Int): OperationResult = getResult { service.getComic(id) }
    override suspend fun getSerie(id: Int): OperationResult = getResult { service.getSerie(id) }


}