package com.br444n.dragonball.data.remote

import com.br444n.dragonball.data.remote.models.Character
import com.br444n.dragonball.data.remote.models.CharactersResponse
import com.br444n.dragonball.data.remote.models.PlanetsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 58,
        @Query("offset") offset: Int = 0,
        @Query("lang") language: String? = null // Si la API soporta parámetro de idioma
    ): CharactersResponse

    @GET("planets")
    suspend fun getPlanets(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("lang") language: String? = null // Si la API soporta parámetro de idioma
    ): PlanetsResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: String,
        @Query("lang") language: String? = null // Si la API soporta parámetro de idioma
    ): Character
}