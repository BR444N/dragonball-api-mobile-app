package com.br444n.dragonball.managers.language

import android.content.Context

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale
import androidx.core.content.edit
import com.br444n.dragonball.R
import com.br444n.dragonball.managers.language.models.Language
import com.br444n.dragonball.managers.language.models.LanguagePrefs

/**
 * Unified Language Manager that handles both UI language and content translation
 * Replaces the separate LanguageManager and ContentManager
 */
object UnifiedLanguageManager {
    
    private val _currentLanguage = MutableStateFlow(LanguagePrefs.DEFAULT_LANGUAGE)
    val currentLanguage: StateFlow<String> = _currentLanguage
    
    private val _isTranslating = MutableStateFlow(false)
    val isTranslating: StateFlow<Boolean> = _isTranslating
    
    private var isInitialized = false
    
    /**
     * Initialize the language manager with context
     */
    fun init(context: Context) {
        if (!isInitialized) {
            migrateFromOldPreferences(context)
            val prefs = context.getSharedPreferences(LanguagePrefs.PREFS_NAME, Context.MODE_PRIVATE)
            val savedLanguage = prefs.getString(LanguagePrefs.KEY_CURRENT_LANGUAGE, LanguagePrefs.DEFAULT_LANGUAGE) ?: LanguagePrefs.DEFAULT_LANGUAGE
            _currentLanguage.value = savedLanguage
            isInitialized = true
        }
    }
    
    /**
     * Set the language for both UI and content
     */
    fun setLanguage(context: Context, languageCode: String) {
        val supportedLanguage = when (languageCode) {
            "en" -> "en"
            "es" -> "es"
            else -> LanguagePrefs.DEFAULT_LANGUAGE
        }
        
        // Save preference
        val prefs = context.getSharedPreferences(LanguagePrefs.PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(LanguagePrefs.KEY_CURRENT_LANGUAGE, supportedLanguage) }
        
        // Update UI language
        updateAppLanguage(context, supportedLanguage)
        
        // Update state (this will trigger content translation in repositories)
        _currentLanguage.value = supportedLanguage
        
        // Restart activity to apply UI language changes completely
        restartActivity(context)
    }
    
    /**
     * Get available languages for selection
     */
    fun getAvailableLanguages(context: Context): List<Language> {
        return listOf(
            Language("es", context.getString(R.string.spanish), "🇪🇸", isDefault = true),
            Language("en", context.getString(R.string.english), "🇺🇸", isDefault = false)
        )
    }
    
    /**
     * Get current language code
     */
    fun getCurrentLanguageCode(): String = _currentLanguage.value
    
    /**
     * Cleanup resources
     */
    fun cleanup() {
        // Any cleanup needed for translation services
    }
    
    private fun updateAppLanguage(context: Context, languageCode: String) {
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)
        
        val resources = context.resources
        val config = resources.configuration.apply {
            setLocale(locale)
        }
        
        // Use createConfigurationContext (minSdkVersion is 26, so always available)
        context.createConfigurationContext(config)
    }
    
    private fun restartActivity(context: Context) {
        if (context is android.app.Activity) {
            context.recreate()
        }
    }
    
    /**
     * Migrate preferences from old LanguageManager and ContentManager
     */
    private fun migrateFromOldPreferences(context: Context) {
        val unifiedPrefs = context.getSharedPreferences(LanguagePrefs.PREFS_NAME, Context.MODE_PRIVATE)
        
        // If unified preferences don't exist, migrate from old ones
        if (!unifiedPrefs.contains(LanguagePrefs.KEY_CURRENT_LANGUAGE)) {
            // Try to get from old LanguageManager
            val oldUIPrefs = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
            val oldContentPrefs = context.getSharedPreferences("content_prefs", Context.MODE_PRIVATE)
            
            val uiLanguage = oldUIPrefs.getString("selected_language", LanguagePrefs.DEFAULT_LANGUAGE)
            val contentLanguage = oldContentPrefs.getString("content_language", LanguagePrefs.DEFAULT_LANGUAGE)
            
            // Prefer content language if they differ, as it's more specific to our use case
            val languageToUse = contentLanguage ?: uiLanguage ?: LanguagePrefs.DEFAULT_LANGUAGE
            
            // Save to unified preferences
            unifiedPrefs.edit { 
                putString(LanguagePrefs.KEY_CURRENT_LANGUAGE, languageToUse) 
            }
        }
    }
}