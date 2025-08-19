package com.br444n.dragonball.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.br444n.dragonball.ui.theme.features.characters.CharacterListScreen
import com.br444n.dragonball.ui.theme.features.characters.CharacterUiState
import com.br444n.dragonball.ui.theme.features.characters.CharactersViewModel
import com.br444n.dragonball.ui.theme.features.characters.detail.CharacterDetailScreen
import com.br444n.dragonball.ui.theme.features.planets.PlanetsScreen
import com.br444n.dragonball.ui.theme.features.settings.SettingsScreen
import com.br444n.dragonball.ui.theme.features.transformations.TransformationsScreen
import com.br444n.dragonball.utils.LoadingAnimation

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val charactersViewModel: CharactersViewModel = viewModel()
    val uiState by charactersViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = AppScreen.CharacterList.route) {
        composable(AppScreen.CharacterList.route) {
            when (uiState) {
                is CharacterUiState.Loading -> {
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
                        onSettingsClick = { navController.navigate(AppScreen.Settings.route) },
                        onMenuItemClick = { route ->
                            when (route) {
                                "characters" -> navController.navigate(AppScreen.CharacterList.route)
                                "Planets" -> navController.navigate(AppScreen.Planets.route)
                                "Transformations" -> navController.navigate(AppScreen.Transformations.route)
                            }
                        },
                        onCharacterClick = { characterId ->
                            navController.navigate(AppScreen.CharacterDetail.createRoute(characterId))
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
        composable(AppScreen.Settings.route) {
            SettingsScreen()
        }
        composable(AppScreen.Planets.route) {
            PlanetsScreen()
        }
        composable(AppScreen.Transformations.route) {
            TransformationsScreen()
        }
        composable(AppScreen.CharacterDetail.route) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")
            if (characterId != null) {
                CharacterDetailScreen(characterId = characterId)
            }
        }
    }
}
