package com.cactus.marvelcomics.ui.character.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cactus.marvelcomics.ui.character.data.CharacterRepository
import com.cactus.marvelcomics.ui.character.domain.MarvelCharacter
import javax.inject.Inject

enum class CharacterListState {
    ShowSuccessView,
    ShowErrorView,
    ShowLoading,
    RemoveLoading
}

class CharactersViewModel @Inject constructor (private val repository: CharacterRepository) : ViewModel() {

    val stateLiveData = MutableLiveData<CharacterListState>()
    val charactersLiveData = MutableLiveData<MutableList<MarvelCharacter>>().apply { value = mutableListOf() }
}