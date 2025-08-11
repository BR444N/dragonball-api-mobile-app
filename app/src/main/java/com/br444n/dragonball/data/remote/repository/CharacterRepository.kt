package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.ApiClient
import com.br444n.dragonball.data.remote.models.CharactersResponse

class CharacterRepository {
    private val api = ApiClient.api

    suspend fun getCharacters(limit:Int = 50, offset: Int = 50): CharactersResponse{
        return api.getCharacters(limit, offset)
    }
}