package com.br444n.dragonball.managers.language.models

/**
 * Data class representing a language option
 */
data class Language(
    val code: String,           // "es", "en"
    val displayName: String,    // "Español", "English"
    val flag: String,          // "🇪🇸", "🇺🇸"
    val isDefault: Boolean = false
)

/**
 * Data class representing the current language state
 */
data class LanguageState(
    val currentLanguage: String,
    val isTranslating: Boolean = false,
    val availableLanguages: List<Language>
)

/**
 * Sealed class for language change events
 */
sealed class LanguageEvent {
    data class LanguageChanged(val newLanguage: String) : LanguageEvent()
    object TranslationStarted : LanguageEvent()
    object TranslationCompleted : LanguageEvent()
    data class TranslationFailed(val error: String) : LanguageEvent()
}

/**
 * Constants for language preferences
 */
object LanguagePrefs {
    const val PREFS_NAME = "unified_language_prefs"
    const val KEY_CURRENT_LANGUAGE = "current_language"
    const val DEFAULT_LANGUAGE = "es"
}