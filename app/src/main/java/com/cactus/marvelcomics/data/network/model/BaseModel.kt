package com.cactus.marvelcomics.data.network.model

    data class DataWrapper (
        val code: Int? = null,
        val status: String? = null,
        var data: DataContainer? = null
    )

    data class DataContainer(
        val limit: Int? = null,
        val count: Int? = null,
        val total: Int? = null,
        val offset: Int? = null,
        var results: List<MarvelCharacter>? = null
    )

    data class MarvelCharacter(
        var id: Int? = null,
        var name: String? = null,
        var description: String? = null,
        var thumbnail: MarvelImage? = null,


        var series: BaseList? = null,
        var comics: BaseList? = null
    )

    data class MarvelImage(
        val path: String? = null,
        val extension: String? = null,
        val uri: String? = "$path.$extension"
    )

    data class BaseList(
        val items: List<BaseSummary>? = null
    )

    data class BaseSummary(
        val resourceURI: String? = null,
        val name: String? = null
    )

