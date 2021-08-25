package com.cactus.marvelcomics.features.characters.domain.listCharacters

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.cactus.marvelcomics.common.CallClickCharacter
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter

class ItemCharacterViewModel {

    var marvelCharacter: MarvelCharacter? = null

    // Observables
    var name: ObservableField<String> = ObservableField()
    var thumbnail: ObservableField<String> = ObservableField()
    var isFavorite : ObservableBoolean = ObservableBoolean(false)

    // Calls
    var callOnClick: CallClickCharacter = null
    var callSetFavorite: CallClickCharacter = null

    fun onClick() {
        callOnClick?.invoke(this)
    }

    fun setFavorite() {
        callSetFavorite?.invoke(this)
    }

}