package com.br444n.dragonball.managers.language

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.edit

/**
 * Maneja el contenido que viene de la API
 * Separado del LanguageManager que maneja solo la UI
 */
object ContentManager {
    private const val PREFS_NAME = "content_prefs"
    private const val KEY_CONTENT_LANGUAGE = "content_language"
    
    private val _contentLanguage = MutableStateFlow("es") // Default español
    val contentLanguage: StateFlow<String> = _contentLanguage
    
    private var isInitialized = false
    
    fun init(context: Context) {
        if (!isInitialized) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val savedLanguage = prefs.getString(KEY_CONTENT_LANGUAGE, "es") ?: "es"
            _contentLanguage.value = savedLanguage
            isInitialized = true
        }
    }
    
    fun setContentLanguage(context: Context, languageCode: String) {
        // Solo permitir idiomas que la API realmente soporte
        val supportedLanguage = when (languageCode) {
            "en" -> "en"
            "es" -> "es"
            else -> "es" // Default
        }
        
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_CONTENT_LANGUAGE, supportedLanguage) }
        _contentLanguage.value = supportedLanguage
    }
    
    fun getAvailableContentLanguages(context: Context): List<Language> {
        return listOf(
            Language("es", context.getString(com.br444n.dragonball.R.string.content_in_spanish), "🇪🇸"),
            Language("en", context.getString(com.br444n.dragonball.R.string.content_in_english), "🇺🇸")
        )
    }
    
    /**
     * Función para mostrar un disclaimer sobre el idioma del contenido
     */
    fun getContentLanguageDisclaimer(context: Context, uiLanguage: String, contentLanguage: String): String? {
        return if (uiLanguage != contentLanguage) {
            when (contentLanguage) {
                "es" -> context.getString(com.br444n.dragonball.R.string.language_disclaimer_content_spanish)
                "en" -> context.getString(com.br444n.dragonball.R.string.language_disclaimer_content_english)
                else -> null
            }
        } else null
    }
}

@Composable
fun getCurrentContentLanguage(): String {
    val context = LocalContext.current
    val contentLanguage by ContentManager.contentLanguage.collectAsState()
    
    // Inicializar si es necesario
    if (contentLanguage.isEmpty()) {
        ContentManager.init(context)
    }
    
    return contentLanguage
}