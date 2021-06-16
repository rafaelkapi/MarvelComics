package com.cactus.marvelcomics.ui.character.domain.listCharacters

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.cactus.marvelcomics.common.CallClickCharacter

class ItemCharacterViewModel {

    var name: ObservableField<String> = ObservableField()
    var thumbnail: ObservableField<String> = ObservableField()
    var favorite: Boolean = false
    var isFavorite : ObservableBoolean = ObservableBoolean(favorite)

    // Calls
    var callOnClick: CallClickCharacter = null
    var callSetFavorite: CallClickCharacter = null

    fun onClick() {
        callOnClick?.invoke(this)
    }

    fun setFavorite() {
        callSetFavorite?.invoke(this)
        isFavorite.set(!favorite)
    }

}