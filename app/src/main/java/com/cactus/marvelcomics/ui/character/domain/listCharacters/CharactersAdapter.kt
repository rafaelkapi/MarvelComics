package com.cactus.marvelcomics.ui.character.domain.listCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.databinding.ItemCharacterBinding
import com.cactus.marvelcomics.common.CallClickCharacter
import com.cactus.marvelcomics.data.network.model.MarvelCharacter


class CharactersAdapter(
    private val onClick: CallClickCharacter = null,
    private val setFavorite: CallClickCharacter = null
) : RecyclerView.Adapter<CharactersViewHolder>() {


    var list: List<MarvelCharacter> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        val viewModel = ItemCharacterViewModel()
        binding.viewmodel = viewModel
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        var character = list[position]
        holder.bind(character, onClick, setFavorite)
    }

    override fun getItemCount(): Int = list.size
}

class CharactersViewHolder(private val _binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(_binding.root) {

    fun bind(
        _character: MarvelCharacter,
        onClick: CallClickCharacter,
        setFavorite: CallClickCharacter
    ) {
        _binding.viewmodel?.apply {
            name.set(_character.name)
            thumbnail.set(_character.thumbnail?.uri
            )

            callOnClick = onClick
            callSetFavorite = setFavorite
        }
        _binding.invalidateAll()
    }
}