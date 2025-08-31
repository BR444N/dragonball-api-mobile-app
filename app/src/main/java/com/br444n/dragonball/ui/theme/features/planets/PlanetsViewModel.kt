package com.br444n.dragonball.ui.theme.features.planets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br444n.dragonball.data.remote.repository.PlanetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetsViewModel : ViewModel() {
    private val repository = PlanetRepository()
    private val _uiState = MutableStateFlow<PlanetUiState>(PlanetUiState.Loading)
    val uiState: StateFlow<PlanetUiState> = _uiState

    init {
        fetchPlanets()
    }

    private fun fetchPlanets() {
        viewModelScope.launch {
            try {
                val response = repository.getPlanets(20, 0)
                if (response.items.isEmpty()) {
                    _uiState.value = PlanetUiState.Empty
                } else {
                    _uiState.value = PlanetUiState.Success(response.items)
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "No internet connection"
                    is java.net.SocketTimeoutException -> "Connection timeout"
                    else -> e.localizedMessage ?: "Unknown error"
                }
                _uiState.value = PlanetUiState.Error(errorMessage)
            }
        }
    }

    fun refreshPlanets() {
        fetchPlanets()
    }
}