package com.cactus.marvelcomics.features.favorites.presentation

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cactus.marvelcomics.common.base.BaseViewModel
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.characters.domain.listCharacters.ItemCharacterViewModel
import com.cactus.marvelcomics.features.favorites.data.FavoritesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) :
    BaseViewModel() {

    // LiveDatas & Observables
    val charactersLiveData =
        MutableLiveData<MutableList<MarvelCharacter>>().apply { value = mutableListOf() }
    var layoutRecyclerView : ObservableBoolean = ObservableBoolean(true)

    // Calls
    var loadingShow: (()-> Unit)? = null
    var loadingHide: (()-> Unit)? = null
    var changeLayout: (() -> Unit)? = null
    var alertDialogExcludeFavorite: ((() -> Unit) -> Unit)? = null
    var callNavigateDetailsFragment: ((character: MarvelCharacter) -> Unit)? = null
    var showNotificationView: ((text: String) -> Unit)? = null

    // Vars
    private var offset = 0


    override fun onCreate() {
        super.onCreate()
        getCharactersFavorites()
    }


    private fun deleteFavoriteCharacter(itemCharacter: ItemCharacterViewModel) {
        alertDialogExcludeFavorite?.let { it { deleteFavorite(itemCharacter) } }
    }


    fun deleteFavorite(itemCharacter: ItemCharacterViewModel) {
        viewModelScope.launch {
            itemCharacter.marvelCharacter?.let {
                repository.deleteCharacter(it)

                charactersLiveData.apply {
                    value?.remove(it)
                    value = charactersLiveData.value
                }
            }
        }
    }


     fun getCharactersFavorites() {
        viewModelScope.launch {
            charactersLiveData.value =
                repository.getCharactersFavorites() as MutableList<MarvelCharacter>
        }
    }


    fun changeLayoutRecyclerView() {
        layoutRecyclerView.set(!layoutRecyclerView.get())
        changeLayout?.invoke()
    }


    fun onClickCharacter(itemCharacter: ItemCharacterViewModel) {
        itemCharacter.marvelCharacter?.let { callNavigateDetailsFragment?.invoke(it) }
    }


    fun setFavorite(itemCharacter: ItemCharacterViewModel) {
        itemCharacter.apply {
            if (isFavorite.get()) {
                deleteFavoriteCharacter(this)
            }
        }
    }


    fun swiperRefreshListCharacter() {
        getCharactersFavorites()
    }

}