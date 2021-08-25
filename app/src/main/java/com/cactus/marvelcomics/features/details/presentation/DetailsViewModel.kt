package com.cactus.marvelcomics.features.details.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cactus.marvelcomics.common.base.BaseViewModel
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.network.model.Comic
import com.cactus.marvelcomics.data.network.model.Serie
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.comics_series.domain.RequestComicsAndSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val requestComicsAndSeries: RequestComicsAndSeries) :
    BaseViewModel() {

    val character = MutableLiveData<MarvelCharacter>()
    val comicsLiveData = MediatorLiveData<List<Comic>>()
    val seriesLiveData = MediatorLiveData<List<Serie>>()

    override fun onCreate() {
        super.onCreate()
        getComics()
        getSeries()
    }


    private fun getComics() {

        character.value?.comicsDb.let {
            if (it.isNullOrEmpty()) {

                viewModelScope.launch {
                    val listComics = withContext(Dispatchers.IO) {
                        requestComicsAndSeries.getComicListLiveData(character.value?.comics ?: listOf())
                    }
                    comicsLiveData.value = listComics
                }

            } else {
                comicsLiveData.value = it
            }
        }
    }


    private fun getSeries() {

        character.value?.seriesDb.let {
            if (it.isNullOrEmpty()) {

                viewModelScope.launch {
                    val listComics = withContext(Dispatchers.IO) {
                        requestComicsAndSeries.getSerieListLiveData(character.value?.series ?: listOf())
                    }
                    seriesLiveData.value = listComics
                }

            } else {
                seriesLiveData.value = it
            }
        }
    }


}