package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.ApiClient
import com.br444n.dragonball.data.remote.models.PlanetsResponse
import com.br444n.dragonball.data.remote.models.translateTo
import com.br444n.dragonball.managers.language.UnifiedLanguageManager

class PlanetRepository {
    private val api = ApiClient.api

    suspend fun getPlanets(
        limit: Int = 20, 
        offset: Int = 0,
        contentLanguage: String? = null
    ): PlanetsResponse {
        // La API siempre devuelve datos en español, así que no pasamos el parámetro de idioma
        val response = api.getPlanets(limit, offset)
        
        // Traducir si es necesario
        val language = contentLanguage ?: UnifiedLanguageManager.currentLanguage.value
        if (language != "es") {
            val translatedPlanets = response.items.map { planet ->
                planet.translateTo(language)
            }
            return response.copy(items = translatedPlanets)
        }
        
        return response
    }
}