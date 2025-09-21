package com.br444n.dragonball.ui.theme.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.components.*
import com.br444n.dragonball.ui.theme.Red
import com.br444n.dragonball.managers.language.UnifiedLanguageManager
import com.br444n.dragonball.managers.theme.ThemeManager
import com.br444n.dragonball.managers.theme.getCurrentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val currentLanguage by UnifiedLanguageManager.currentLanguage.collectAsState()
    val isTranslating by UnifiedLanguageManager.isTranslating.collectAsState()
    val availableLanguages = UnifiedLanguageManager.getAvailableLanguages(context)
    val isDarkMode = getCurrentTheme()
    var showLanguageDialog by remember { mutableStateOf(false) }
    
    // El manager ya está inicializado en MainActivity
    LaunchedEffect(Unit) {
        UnifiedLanguageManager.init(context)
    }
    
    Scaffold(
        topBar = {
            CharacterDetailAppBar(
                title = stringResource(R.string.settings),
                onBackClick = onBackClick,
                titleColor = Red
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Sección de Apariencia
            SettingsSection(
                title = stringResource(R.string.appearance)
            ) {
                ThemeSettingItem(
                    isDarkMode = isDarkMode,
                    onToggle = { darkMode ->
                        ThemeManager.setDarkMode(context, darkMode)
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sección de Idioma (Unificado)
            SettingsSection(
                title = stringResource(R.string.language)
            ) {
                // Idioma unificado (UI + Contenido)
                UnifiedLanguageSettingItem(
                    currentLanguage = currentLanguage,
                    languages = availableLanguages,
                    isTranslating = isTranslating,
                    onLanguageClick = { showLanguageDialog = true }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sección de información de la app
            SettingsSection(
                title = stringResource(R.string.app_information)
            ) {
                AppInfoItem()
            }
        }
        
        // Diálogo de selección de idioma unificado
        if (showLanguageDialog) {
            LanguageSelectionDialog(
                title = stringResource(R.string.select_language),
                currentLanguage = currentLanguage,
                languages = availableLanguages,
                onLanguageSelected = { languageCode ->
                    UnifiedLanguageManager.setLanguage(context, languageCode)
                    showLanguageDialog = false
                },
                onDismiss = { showLanguageDialog = false }
            )
        }
    }
}

