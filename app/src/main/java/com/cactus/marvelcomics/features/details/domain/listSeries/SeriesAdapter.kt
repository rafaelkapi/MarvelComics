package com.cactus.marvelcomics.features.details.domain.listSeries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.common.bindImageUrl
import com.cactus.marvelcomics.data.network.model.Serie
import com.cactus.marvelcomics.databinding.ItemSeriesBinding

class SeriesAdapter() : RecyclerView.Adapter<SeriesViewHolder>() {

    var list: List<Serie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSeriesBinding.inflate(layoutInflater, parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        var serie = list[position]
        holder.bind(serie)
    }

    override fun getItemCount(): Int = list.size
}

class SeriesViewHolder(private val binding: ItemSeriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(serie: Serie) {
        binding.ivSerie.bindImageUrl(serie.thumbnail?.uri)
    }
}