package com.br444n.dragonball.data.remote.models

data class Character(
    val id: Int,
    val name: String,
    val ki: String?,
    val maxKi: String?,
    val race: String?,
    val gender: String?,
    val description: String?,
    val image: String?,
    val affiliation: String?
)

data class CharactersResponse(
    val items: List<Character>,
    val meta: Meta
)

data class Meta(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

