package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.ApiClient
import com.br444n.dragonball.data.remote.models.CharactersResponse
import com.br444n.dragonball.managers.language.ContentManager

class CharacterRepository {
    private val api = ApiClient.api

    suspend fun getCharacters(
        limit: Int = 58, 
        offset: Int = 0,
        contentLanguage: String? = null
    ): CharactersResponse {
        // Usar el idioma de contenido si se proporciona, sino usar el del ContentManager
        val language = contentLanguage ?: ContentManager.contentLanguage.value
        return api.getCharacters(limit, offset, language)
    }

    suspend fun getCharacterById(
        id: String,
        contentLanguage: String? = null
    ): com.br444n.dragonball.data.remote.models.Character {
        // Usar el idioma de contenido si se proporciona, sino usar el del ContentManager
        val language = contentLanguage ?: ContentManager.contentLanguage.value
        return api.getCharacterById(id, language)
    }
}