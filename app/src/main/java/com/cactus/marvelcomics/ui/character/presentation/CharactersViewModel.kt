package com.cactus.marvelcomics.ui.character.presentation

import androidx.lifecycle.*
import com.cactus.marvelcomics.common.base.BaseViewModel
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.OperationResult.*
import com.cactus.marvelcomics.data.network.model.MarvelCharacter
import com.cactus.marvelcomics.data.resultLiveData
import com.cactus.marvelcomics.ui.character.data.CharacterRepository
import com.cactus.marvelcomics.ui.character.domain.listCharacters.ItemCharacterViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import javax.inject.Inject

enum class CharacterListState {
    ShowSuccessView,
    ShowErrorView,
    ShowLoading,
    RemoveLoading
}

class CharactersViewModel @Inject constructor(private val repository: CharacterRepository) :
    BaseViewModel() {

    val stateLiveData = MutableLiveData<CharacterListState>()
    val charactersLiveData =
        MutableLiveData<MutableList<MarvelCharacter>>().apply { value = mutableListOf() }
    val operationResultLiveData = MediatorLiveData<OperationResult>()
    private var offset = 0

    companion object {
        private const val DEFAULT_LIMIT = 20
    }

    override fun onCreate() {
        super.onCreate()
        getCharacters()
    }


    private fun getCharacters() {

        val resultLD =
            resultLiveData(null, { repository.getCharacterList(DEFAULT_LIMIT, offset) }, null)
        operationResultLiveData.addSource(resultLD) { operationResultLiveData.value = it }
    }

    //    fun onLastItemVisible(lastItemVisible: Int) {
//        charactersLiveData.value?.size
//            ?.takeIf { lastItemVisible == it - 1 }
//            ?.run {
//                offset += DEFAULT_LIMIT
//                getCharacters()
//            }
//    }
    fun onLastItemVisible() {
        offset += DEFAULT_LIMIT
        getCharacters()
    }

    private fun addTabLayoutMediator() {

//        TabLayoutMediator(
//            tabLayout, viewPager
//        ) { tab: TabLayout.Tab, position: Int ->
//            tab.text = listOfTitles[position]
//        }.attach()
    }


    fun onClickCharacter(itemCharacter: ItemCharacterViewModel) {}
    fun setFavorite(itemCharacter: ItemCharacterViewModel) {}
}