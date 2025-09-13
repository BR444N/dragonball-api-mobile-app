package com.br444n.dragonball.managers.language

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "selected_language"
    
    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage
    
    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        _currentLanguage.value = savedLanguage
    }
    
    fun setLanguage(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
        _currentLanguage.value = languageCode
        
        // Actualizar la configuración de la app
        updateAppLanguage(context, languageCode)
    }
    
    private fun updateAppLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }
    
    fun getAvailableLanguages(): List<Language> {
        return listOf(
            Language("en", "English", "🇺🇸"),
            Language("es", "Español", "🇪🇸"),
            Language("fr", "Français", "🇫🇷"),
            Language("de", "Deutsch", "🇩🇪"),
            Language("ja", "日本語", "🇯🇵")
        )
    }
}

data class Language(
    val code: String,
    val name: String,
    val flag: String
)

@Composable
fun getCurrentLanguage(): String {
    val context = LocalContext.current
    val currentLanguage by LanguageManager.currentLanguage.collectAsState()
    
    // Inicializar si es necesario
    if (currentLanguage.isEmpty()) {
        LanguageManager.init(context)
    }
    
    return currentLanguage
}