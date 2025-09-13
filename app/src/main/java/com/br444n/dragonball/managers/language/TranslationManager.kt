package com.br444n.dragonball.managers.language

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TranslationManager {
    private const val PREFS_NAME = "translation_prefs"
    private const val KEY_TRANSLATION_ENABLED = "translation_enabled"
    
    private val _isTranslationEnabled = MutableStateFlow(false)
    val isTranslationEnabled: StateFlow<Boolean> = _isTranslationEnabled
    
    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val enabled = prefs.getBoolean(KEY_TRANSLATION_ENABLED, false)
        _isTranslationEnabled.value = enabled
    }
    
    fun setTranslationEnabled(context: Context, enabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_TRANSLATION_ENABLED, enabled).apply()
        _isTranslationEnabled.value = enabled
    }
    
    // Función placeholder para traducción con ML Kit
    // TODO: Implementar ML Kit Translation API
    suspend fun translateText(text: String, targetLanguage: String): String {
        // Por ahora retorna el texto original
        // Aquí se implementaría la lógica de ML Kit
        return text
    }
    
    fun getLanguageCodeForMLKit(languageCode: String): String {
        return when (languageCode) {
            "en" -> "en"
            "es" -> "es"
            "fr" -> "fr"
            "de" -> "de"
            "ja" -> "ja"
            else -> "en"
        }
    }
}