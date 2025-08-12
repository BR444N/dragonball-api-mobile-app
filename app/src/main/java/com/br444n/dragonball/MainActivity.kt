package com.br444n.dragonball

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.br444n.dragonball.ui.theme.DragonBallTheme
import com.br444n.dragonball.ui.theme.features.characters.CharacterListScreen
import com.br444n.dragonball.ui.theme.features.characters.CharacterUiState
import com.br444n.dragonball.ui.theme.features.characters.CharactersViewModel
import com.br444n.dragonball.utils.LoadingAnimation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonBallTheme {
                val charactersViewModel: CharactersViewModel = viewModel()
                val uiState by charactersViewModel.uiState.collectAsState()

                when (uiState) {
                    is CharacterUiState.Loading -> {
                        // Aquí un indicador de carga
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation()
                        }
                    }
                    is CharacterUiState.Success -> {
                        val characters = (uiState as CharacterUiState.Success).characters
                        CharacterListScreen(
                            characters = characters,
                            onSettingsClick = {
                                Log.d("MainActivity", "Settings clicked")
                            },
                            onMenuItemClick = { selectedItem ->
                                Log.d("MainActivity", "Menu item clicked: $selectedItem")
                            }
                        )
                    }
                    is CharacterUiState.Error -> {
                        val message = (uiState as CharacterUiState.Error).message
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error: $message", color = Color.Red)
                        }
                    }
                    is CharacterUiState.Empty -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No characters found")
                        }
                    }
                }
            }
        }
    }
}



