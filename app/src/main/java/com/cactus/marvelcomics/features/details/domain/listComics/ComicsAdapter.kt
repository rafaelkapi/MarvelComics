package com.cactus.marvelcomics.features.details.domain.listComics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.common.bindImageUrl
import com.cactus.marvelcomics.data.network.model.Comic
import com.cactus.marvelcomics.databinding.ItemComicsBinding

class ComicsAdapter() : RecyclerView.Adapter<ComicsViewHolder>() {

    var list: List<Comic> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemComicsBinding.inflate(layoutInflater, parent, false)
        return ComicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        var comic = list[position]
        holder.bind(comic)
    }

    override fun getItemCount(): Int = list.size
}

class ComicsViewHolder(private val binding: ItemComicsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(comic: Comic) {
        binding.ivComic.bindImageUrl(comic.thumbnail?.uri)
    }
}