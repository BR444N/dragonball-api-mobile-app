package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.ApiClient
import com.br444n.dragonball.data.remote.models.CharactersResponse
import com.br444n.dragonball.data.remote.models.translateTo
import com.br444n.dragonball.managers.language.UnifiedLanguageManager

class CharacterRepository {
    private val api = ApiClient.api

    suspend fun getCharacters(
        limit: Int = 58, 
        offset: Int = 0,
        contentLanguage: String? = null
    ): CharactersResponse {
        // La API siempre devuelve datos en español, así que no pasamos el parámetro de idioma
        val response = api.getCharacters(limit, offset)
        
        // Traducir si es necesario
        val language = contentLanguage ?: UnifiedLanguageManager.currentLanguage.value
        if (language != "es") {
            val translatedCharacters = response.items.map { character ->
                character.translateTo(language)
            }
            return response.copy(items = translatedCharacters)
        }
        
        return response
    }

    suspend fun getCharacterById(
        id: String,
        contentLanguage: String? = null
    ): com.br444n.dragonball.data.remote.models.Character {
        // La API siempre devuelve datos en español, así que no pasamos el parámetro de idioma
        val character = api.getCharacterById(id)
        
        // Traducir si es necesario
        val language = contentLanguage ?: UnifiedLanguageManager.currentLanguage.value
        return if (language != "es") {
            character.translateTo(language)
        } else {
            character
        }
    }
}