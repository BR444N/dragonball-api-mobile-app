package com.br444n.dragonball.ui.theme.features.planets

import com.br444n.dragonball.data.remote.models.Planet

sealed class PlanetUiState {
    object Loading : PlanetUiState()
    data class Success(val planets: List<Planet>) : PlanetUiState()
    data class Error(val message: String) : PlanetUiState()
    object Empty : PlanetUiState()
    object NoInternetConnection : PlanetUiState()
}