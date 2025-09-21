package com.br444n.dragonball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.br444n.dragonball.navigation.AppNavigation
import com.br444n.dragonball.ui.theme.DragonBallTheme
import com.br444n.dragonball.managers.theme.ThemeManager
import com.br444n.dragonball.managers.language.UnifiedLanguageManager
import com.br444n.dragonball.services.TranslationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inicializar managers
        UnifiedLanguageManager.init(this)
        ThemeManager.init(this)
        setContent {
            val isDarkMode by ThemeManager.isDarkMode.collectAsState()
            DragonBallTheme(darkTheme = isDarkMode) {
                AppNavigation()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Limpiar recursos del servicio de traducción y manager unificado
        TranslationService.cleanup()
        UnifiedLanguageManager.cleanup()
    }
}