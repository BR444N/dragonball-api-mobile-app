package com.br444n.dragonball.ui.theme.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br444n.dragonball.data.remote.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val repository = CharacterRepository()
    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        _uiState.value = CharacterUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(58, 0)
                if (response.items.isEmpty()) {
                    _uiState.value = CharacterUiState.Empty
                } else {
                    _uiState.value = CharacterUiState.Success(response.items)
                }
            } catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException -> {
                        _uiState.value = CharacterUiState.NoInternetConnection
                    }
                    else -> {
                        val errorMessage = when (e) {
                            is java.net.SocketTimeoutException -> "Connection timeout"
                            else -> e.localizedMessage ?: "Unknown error"
                        }
                        _uiState.value = CharacterUiState.Error(errorMessage)
                    }
                }
            }
        }
    }

    fun retry() {
        fetchCharacters()
    }
}