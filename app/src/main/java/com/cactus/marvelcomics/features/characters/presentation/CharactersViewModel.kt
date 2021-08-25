package com.cactus.marvelcomics.features.characters.presentation

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseViewModel
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.network.model.CharacterDataWrapper
import com.cactus.marvelcomics.features.characters.data.CharacterRepository
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.characters.domain.listCharacters.ItemCharacterViewModel
import com.cactus.marvelcomics.features.comics_series.domain.RequestComicsAndSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CharactersViewModel @Inject constructor(
    private val context: Context,
    private val repository: CharacterRepository,
    private val requestComicsAndSeries: RequestComicsAndSeries
) :
    BaseViewModel() {

    // LiveDatas & Observables
    val charactersLiveData =
        MutableLiveData<MutableList<MarvelCharacter>>().apply { value = mutableListOf() }
    val favoritesIdLiveData =
        MutableLiveData<MutableList<Long>>().apply { value = mutableListOf(initialListForCharacter) }
    val operationResultLiveData = MediatorLiveData<OperationResult>()
    var layoutRecyclerView : ObservableBoolean = ObservableBoolean(true)

    // Calls
    var loadingShow: (() -> Unit)? = null
    var loadingHide: (() -> Unit)? = null
    var reloadRecyclerView: (() -> Unit)? = null
    var changeLayout: (() -> Unit)? = null
    var alertDialogExcludeFavorite: ((() -> Unit) -> Unit)? = null
    var callNavigateDetailsFragment: ((character: MarvelCharacter) -> Unit)? = null
    var showNotificationView: ((text: String) -> Unit)? = null

    // Vars
    private var offset = 0
    private val initialListForCharacter = 1L
    private var afterBoot = 0


    override fun onCreate() {
        super.onCreate()
        getListFavoriteId()
        getCharacters()
    }


    private fun getCharacters() {
        operationResultLiveData.addSource(liveData(Dispatchers.IO) {

            emit(OperationResult.Loading)

            repository.getCharacterList(DEFAULT_LIMIT, offset).let { response ->

                when (response) {
                    is OperationResult.Success<*> -> {
                        val dataWrapper = response.data as CharacterDataWrapper
                        emit(OperationResult.Success(dataWrapper.data?.results))
                    }
                    is OperationResult.Error -> {
                        emit(response)
                    }
                    else -> {}
                }
            }
        }) { operationResultLiveData.value = it }
    }


    private fun getListFavoriteId() {
        val lisOut = mutableListOf<Long>()
        viewModelScope.launch {
            lisOut.addAll(repository.getCharactersFavorites().map { it.id })
            if(lisOut.isNullOrEmpty()) lisOut.add(0)
            favoritesIdLiveData.value = lisOut
        }
    }


    private fun favoriteCharacters(character: MarvelCharacter) {
        favoritesIdLiveData.value?.add(character.id)

        viewModelScope.launch {
            showNotificationView?.invoke("${context.getString(R.string.downloading)} ${character.name} ...")

            val listComics = withContext(Dispatchers.IO) {
                requestComicsAndSeries.getComicListLiveData(character.comics)
            }

            val listSeries = withContext(Dispatchers.IO) {
                requestComicsAndSeries.getSerieListLiveData(character.series)
            }

            repository.saveCharacter(character.apply {
                comicsDb = listComics
                seriesDb = listSeries
            })
            showNotificationView?.invoke("${character.name} ${context.getString(R.string.saved)}")
        }
    }


    private fun deleteFavoriteCharacter(itemCharacter: ItemCharacterViewModel) {
        alertDialogExcludeFavorite?.let { it { deleteFavorite(itemCharacter) } }
    }

    private fun deleteFavorite(itemCharacter: ItemCharacterViewModel) {
        viewModelScope.launch {
            itemCharacter.marvelCharacter?.let {
                repository.deleteCharacter(it)
                favoritesIdLiveData.value?.remove(it.id)
                itemCharacter.isFavorite.set(false)
                showNotificationView?.invoke("${it.name} ${context.getString(R.string.removed_from_favorites)}")
            }
        }
    }


    fun onLastItemVisible() {
        offset += DEFAULT_LIMIT
        getCharacters()
    }


    fun onClickCharacter(itemCharacter: ItemCharacterViewModel) {
        itemCharacter.marvelCharacter?.let { callNavigateDetailsFragment?.invoke(it) }
    }


    fun setFavorite(itemCharacter: ItemCharacterViewModel) {
        itemCharacter.apply {
            if (isFavorite.get()) {
                deleteFavoriteCharacter(this)
            } else {
                isFavorite.set(!isFavorite.get())
                favoriteCharacters(this.marvelCharacter!!)
            }
        }
    }


    fun changeLayoutRecyclerView() {
        layoutRecyclerView.set(!layoutRecyclerView.get())
        changeLayout?.invoke()
    }


    fun swiperRefreshListCharacter() {
        getListFavoriteId()
        getCharacters()
    }


    override fun onResume() {
        super.onResume()
        afterBoot++
        if (afterBoot > 1) {
           getListFavoriteId()
        }
    }


    companion object {
        private const val DEFAULT_LIMIT = 20
    }

}