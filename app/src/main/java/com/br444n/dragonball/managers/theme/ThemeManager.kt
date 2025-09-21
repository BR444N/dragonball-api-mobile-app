package com.br444n.dragonball.managers.theme

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.edit

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_DARK_MODE = "dark_mode"
    
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode
    
    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val darkMode = prefs.getBoolean(KEY_DARK_MODE, false)
        _isDarkMode.value = darkMode
    }
    
    fun setDarkMode(context: Context, isDark: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_DARK_MODE, isDark) }
        _isDarkMode.value = isDark
    }
    
    fun toggleTheme(context: Context) {
        setDarkMode(context, !_isDarkMode.value)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun getCurrentTheme(): Boolean {
    val context = LocalContext.current
    val isDarkMode by ThemeManager.isDarkMode.collectAsState()
    
    // Inicializar si es necesario
    if (!ThemeManager.isDarkMode.value) {
        ThemeManager.init(context)
    }
    
    return isDarkMode
}