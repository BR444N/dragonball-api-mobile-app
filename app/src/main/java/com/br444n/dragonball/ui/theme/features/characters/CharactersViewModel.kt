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
        viewModelScope.launch {
            try {
                val response = repository.getCharacters(limit = 50)
                _uiState.value = CharacterUiState.Success(response.items)
            } catch (e: Exception) {
                _uiState.value = CharacterUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}