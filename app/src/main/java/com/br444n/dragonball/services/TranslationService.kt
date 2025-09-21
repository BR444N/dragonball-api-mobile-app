package com.br444n.dragonball.services

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object TranslationService {
    private var spanishToEnglishTranslator: Translator? = null
    
    private fun getSpanishToEnglishTranslator(): Translator {
        if (spanishToEnglishTranslator == null) {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.SPANISH)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
            spanishToEnglishTranslator = Translation.getClient(options)
        }
        return spanishToEnglishTranslator!!
    }
    
    suspend fun translateToEnglish(text: String): String {
        if (text.isBlank()) return text
        
        return suspendCancellableCoroutine { continuation ->
            val translator = getSpanishToEnglishTranslator()
            
            // Descargar el modelo si es necesario
            translator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    // Traducir el texto
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            continuation.resume(translatedText)
                        }
                        .addOnFailureListener { _ ->
                            // Si falla la traducción, devolver el texto original
                            continuation.resume(text)
                        }
                }
                .addOnFailureListener { _ ->
                    // Si falla la descarga del modelo, devolver el texto original
                    continuation.resume(text)
                }
        }
    }
    
    suspend fun translateTextIfNeeded(text: String?, targetLanguage: String): String? {
        if (text.isNullOrBlank()) return text
        
        return when (targetLanguage) {
            "en" -> translateToEnglish(text)
            "es" -> text // Ya está en español
            else -> text // Idioma no soportado, devolver original
        }
    }
    
    fun cleanup() {
        spanishToEnglishTranslator?.close()
        spanishToEnglishTranslator = null
    }
}