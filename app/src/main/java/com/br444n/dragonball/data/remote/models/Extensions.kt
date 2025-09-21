package com.br444n.dragonball.data.remote.models

import com.br444n.dragonball.services.TranslationService

/**
 * Extensiones para traducir modelos de datos
 */

suspend fun Character.translateTo(language: String): Character {
    if (language == "es") return this // Ya está en español
    
    return this.copy(
        description = TranslationService.translateTextIfNeeded(this.description, language)
    )
}

suspend fun Planet.translateTo(language: String): Planet {
    if (language == "es") return this // Ya está en español
    
    return this.copy(
        description = TranslationService.translateTextIfNeeded(this.description, language)
    )
}

suspend fun Transformation.translateTo(language: String): Transformation {
    if (language == "es") return this // Ya está en español
    
    return this.copy(
        // Las transformaciones generalmente no tienen descripción para traducir
        // pero si la tuvieran, se traduciría aquí
    )
}