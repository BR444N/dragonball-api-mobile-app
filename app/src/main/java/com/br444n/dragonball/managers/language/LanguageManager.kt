package com.br444n.dragonball.managers.language

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import androidx.core.content.edit

object LanguageManager {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "selected_language"
    
    private val _currentLanguage = MutableStateFlow("es")
    val currentLanguage: StateFlow<String> = _currentLanguage
    
    private var isInitialized = false
    
    fun init(context: Context) {
        if (!isInitialized) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val savedLanguage = prefs.getString(KEY_LANGUAGE, "es") ?: "es"
            _currentLanguage.value = savedLanguage
            isInitialized = true
        }
    }
    
    fun applyCurrentLanguage(context: Context) {
        updateAppLanguage(context, _currentLanguage.value)
    }
    
    // Método para debug
    fun getCurrentLanguageCode(): String = _currentLanguage.value
    
    fun setLanguage(context: Context, languageCode: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_LANGUAGE, languageCode) }
        _currentLanguage.value = languageCode
        
        // Actualizar la configuración de la app
        updateAppLanguage(context, languageCode)
        
        // Reiniciar la actividad para aplicar el cambio de idioma completamente
        restartActivity(context)
    }
    
    private fun restartActivity(context: Context) {
        if (context is android.app.Activity) {
            context.recreate()
        }
    }
    
    private fun updateAppLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        
        // Aplicar la configuración
        resources.updateConfiguration(config, resources.displayMetrics)
        
        // También actualizar el contexto de la aplicación si es posible
        try {
            val appContext = context.applicationContext
            appContext.resources.updateConfiguration(config, appContext.resources.displayMetrics)
        } catch (e: Exception) {
            // Ignorar si no se puede actualizar el contexto de la aplicación
        }
    }
    
    fun getAvailableLanguages(): List<Language> {
        return listOf(
            Language("en", "English", "🇺🇸"),
            Language("es", "Español", "🇪🇸")
            // Solo mantener idiomas que realmente soportes en la UI
            // El contenido de la API seguirá siendo en español/inglés
        )
    }
    
    // Función para obtener el idioma preferido para el contenido de la API
    fun getApiContentLanguage(): String {
        return when (_currentLanguage.value) {
            "en" -> "en"
            "es" -> "es"
            else -> "es" // Default a español si la API lo soporta mejor
        }
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