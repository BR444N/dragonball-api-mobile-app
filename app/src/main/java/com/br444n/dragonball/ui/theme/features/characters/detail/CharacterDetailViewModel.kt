package com.br444n.dragonball.ui.theme.features.characters.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br444n.dragonball.data.remote.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val repository = CharacterRepository()
    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState

    fun loadCharacter(characterId: String) {
        _uiState.value = CharacterDetailUiState.Loading
        
        viewModelScope.launch {
            try {
                val character = repository.getCharacterById(characterId)
                _uiState.value = CharacterDetailUiState.Success(character)
            } catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException -> {
                        _uiState.value = CharacterDetailUiState.NoInternetConnection
                    }
                    else -> {
                        val errorMessage = when (e) {
                            is java.net.SocketTimeoutException -> "Connection timeout"
                            else -> e.localizedMessage ?: "Unknown error occurred"
                        }
                        _uiState.value = CharacterDetailUiState.Error(errorMessage)
                    }
                }
            }
        }
    }

    fun retry(characterId: String) {
        loadCharacter(characterId)
    }
}