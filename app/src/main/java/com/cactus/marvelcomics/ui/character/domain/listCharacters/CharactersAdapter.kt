package com.cactus.marvelcomics.ui.character.domain.listCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.common.BindableAdapter
import com.cactus.marvelcomics.databinding.ItemCharacterBinding
import com.cactus.marvelcomics.ui.character.domain.MarvelCharacter
import com.cactus.marvelcomics.common.CallClickCharacter


class CharactersAdapter(
    private val onClick: CallClickCharacter = null,
    private val setFavorite: CallClickCharacter = null
) : RecyclerView.Adapter<CharactersViewHolder>(), BindableAdapter<MarvelCharacter> {

    private var listCharacters = mutableListOf<MarvelCharacter>()

    var list: List<MarvelCharacter> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun setData(items: List<MarvelCharacter>) {
        listCharacters = items as MutableList<MarvelCharacter>
        notifyDataSetChanged()
    }

    override fun deleteItem(position: Int?) {
        position?.let {
            listCharacters.removeAt(it)
            notifyItemRemoved(it)
            notifyItemRangeChanged(it, listCharacters.size)
        }
    }


    override fun changedPositions(positions: Set<Int>) {
        positions.forEach(this::notifyItemChanged)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        val viewModel = ItemCharacterViewModel()
        binding.viewmodel = viewModel
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        var character = listCharacters[position]
        holder.bind(character, onClick, setFavorite)
    }

    override fun getItemCount(): Int = listCharacters.size
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
            thumbnail.set(_character.thumbnail)

            callOnClick = onClick
            callSetFavorite = setFavorite
        }
        _binding.invalidateAll()
    }
}