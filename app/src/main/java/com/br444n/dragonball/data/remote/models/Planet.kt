package com.br444n.dragonball.data.remote.models

data class Planet(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String?,
    val image: String?,
    val deletedAt: String?
)

data class PlanetsResponse(
    val items: List<Planet>,
    val meta: Meta,
    val links: Links
)

data class Links(
    val first: String,
    val previous: String,
    val next: String,
    val last: String
)