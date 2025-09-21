package com.br444n.dragonball.utils

import android.content.Context
import android.util.Log
import com.br444n.dragonball.managers.language.LanguageManager
import com.br444n.dragonball.managers.language.ContentManager

object LanguageTestHelper {
    
    fun logCurrentLanguageState(context: Context) {
        Log.d("LanguageTest", "UI Language: ${LanguageManager.getCurrentLanguageCode()}")
        Log.d("LanguageTest", "Content Language: ${ContentManager.contentLanguage.value}")
        Log.d("LanguageTest", "System Locale: ${context.resources.configuration.locales[0]}")
    }
    
    fun testLanguageChange(context: Context, languageCode: String) {
        Log.d("LanguageTest", "Changing language to: $languageCode")
        LanguageManager.setLanguage(context, languageCode)
        logCurrentLanguageState(context)
    }
    
    fun testContentLanguageChange(context: Context, languageCode: String) {
        Log.d("LanguageTest", "Changing content language to: $languageCode")
        ContentManager.setContentLanguage(context, languageCode)
        logCurrentLanguageState(context)
    }
}