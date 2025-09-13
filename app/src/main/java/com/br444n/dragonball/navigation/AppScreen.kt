package com.br444n.dragonball.navigation

sealed class AppScreen(val route: String) {
    object CharacterList : AppScreen("character_list")
    object Settings : AppScreen("settings")
    object Planets : AppScreen("planets")
    object CharacterDetail : AppScreen("character_detail/{characterId}") {
        fun createRoute(characterId: String) = "character_detail/$characterId"
    }
}