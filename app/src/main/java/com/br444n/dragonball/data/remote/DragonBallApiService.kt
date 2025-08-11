package com.br444n.dragonball.data.remote

import com.br444n.dragonball.data.remote.models.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DragonBallApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): CharactersResponse
}