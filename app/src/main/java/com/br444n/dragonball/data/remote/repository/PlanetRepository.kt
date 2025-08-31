package com.br444n.dragonball.data.remote.repository

import com.br444n.dragonball.data.remote.ApiClient
import com.br444n.dragonball.data.remote.models.PlanetsResponse

class PlanetRepository {
    private val api = ApiClient.api

    suspend fun getPlanets(limit: Int = 20, offset: Int = 0): PlanetsResponse {
        return api.getPlanets(limit, offset)
    }
}