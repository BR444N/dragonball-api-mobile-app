package com.br444n.dragonball.ui.theme.features.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br444n.dragonball.data.remote.repository.CharacterRepository
import com.br444n.dragonball.managers.language.UnifiedLanguageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val repository = CharacterRepository()
    private val _uiState = MutableStateFlow<CharacterUiState>(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> = _uiState

    init {
        fetchCharacters()
        // Observar cambios en el idioma unificado
        viewModelScope.launch {
            UnifiedLanguageManager.currentLanguage.collectLatest { _ ->
                fetchCharacters()
            }
        }
    }

    private fun fetchCharacters() {
        _uiState.value = CharacterUiState.Loading
        viewModelScope.launch {
            try {
                // Usar el idioma unificado actual
                val contentLanguage = UnifiedLanguageManager.currentLanguage.value
                val response = repository.getCharacters(58, 0, contentLanguage)
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
    
    fun reloadWithCurrentLanguage() {
        fetchCharacters()
    }

    fun retry() {
        fetchCharacters()
    }
}