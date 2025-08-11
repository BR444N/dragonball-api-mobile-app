package com.br444n.dragonball.ui.theme.features.characters

import com.br444n.dragonball.data.remote.models.Character

sealed class CharacterUiState {
    object Loading : CharacterUiState()
    data class Success(val characters: List<Character>) : CharacterUiState()
    data class Error(val message: String) : CharacterUiState()
}
