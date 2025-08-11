package com.br444n.dragonball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.br444n.dragonball.ui.theme.DragonBallTheme
import com.br444n.dragonball.ui.theme.features.characters.CharacterListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonBallTheme {
                CharacterListScreen()
            }
        }
    }
}

