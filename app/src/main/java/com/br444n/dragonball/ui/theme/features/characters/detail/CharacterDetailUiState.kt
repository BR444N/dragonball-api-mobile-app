package com.br444n.dragonball.ui.theme.features.characters.detail

import com.br444n.dragonball.data.remote.models.Character

sealed class CharacterDetailUiState {
    object Loading : CharacterDetailUiState()
    data class Success(val character: Character) : CharacterDetailUiState()
    data class Error(val message: String) : CharacterDetailUiState()
}