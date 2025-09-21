package com.br444n.dragonball.ui.theme.features.settings

import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.components.CharacterDetailAppBar
import com.br444n.dragonball.ui.theme.*
import com.br444n.dragonball.managers.language.LanguageManager
import com.br444n.dragonball.managers.theme.ThemeManager
import com.br444n.dragonball.managers.language.Language
import com.br444n.dragonball.managers.theme.getCurrentTheme
import com.br444n.dragonball.managers.language.getCurrentLanguage
import com.br444n.dragonball.managers.language.ContentManager
import com.br444n.dragonball.managers.language.getCurrentContentLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val currentLanguage = getCurrentLanguage()
    val currentContentLanguage = getCurrentContentLanguage()
    val isDarkMode = getCurrentTheme()
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showContentLanguageDialog by remember { mutableStateOf(false) }
    
    // Los managers ya están inicializados en MainActivity
    // Solo asegurar que estén inicializados por si acaso
    LaunchedEffect(Unit) {
        LanguageManager.init(context)
        ContentManager.init(context)
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
            
            // Sección de Idioma
            SettingsSection(
                title = stringResource(R.string.language)
            ) {
                // Idioma de la interfaz
                LanguageSettingItem(
                    title = stringResource(R.string.interface_language),
                    currentLanguage = currentLanguage,
                    languages = LanguageManager.getAvailableLanguages(),
                    onLanguageClick = { showLanguageDialog = true }
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Idioma del contenido (API)
                LanguageSettingItem(
                    title = stringResource(R.string.content_language),
                    currentLanguage = currentContentLanguage,
                    languages = ContentManager.getAvailableContentLanguages(context),
                    onLanguageClick = { showContentLanguageDialog = true }
                )
                
                // Disclaimer si los idiomas son diferentes
                ContentManager.getContentLanguageDisclaimer(context, currentLanguage, currentContentLanguage)?.let { disclaimer ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = disclaimer,
                        style = MaterialTheme.typography.bodySmall,
                        color = Orange2.copy(alpha = 0.8f),
                        modifier = Modifier.padding(start = 36.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sección de información de la app
            SettingsSection(
                title = stringResource(R.string.app_information)
            ) {
                AppInfoItem()
            }
        }
        
        // Diálogo de selección de idioma de UI
        if (showLanguageDialog) {
            LanguageSelectionDialog(
                title = stringResource(R.string.select_interface_language),
                currentLanguage = currentLanguage,
                languages = LanguageManager.getAvailableLanguages(),
                onLanguageSelected = { languageCode ->
                    LanguageManager.setLanguage(context, languageCode)
                    showLanguageDialog = false
                },
                onDismiss = { showLanguageDialog = false }
            )
        }
        
        // Diálogo de selección de idioma de contenido
        if (showContentLanguageDialog) {
            LanguageSelectionDialog(
                title = stringResource(R.string.select_content_language),
                currentLanguage = currentContentLanguage,
                languages = ContentManager.getAvailableContentLanguages(context),
                onLanguageSelected = { languageCode ->
                    ContentManager.setContentLanguage(context, languageCode)
                    showContentLanguageDialog = false
                },
                onDismiss = { showContentLanguageDialog = false }
            )
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Orange,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
private fun LanguageSettingItem(
    title: String,
    currentLanguage: String,
    languages: List<Language>,
    onLanguageClick: () -> Unit
) {
    val currentLang = languages.find { it.code == currentLanguage } ?: languages.first()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Gold
                )
                Text(
                    text = "${currentLang.flag} ${currentLang.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Orange2
                )
            }
        }
    }
}



@Composable
private fun LanguageSelectionDialog(
    title: String,
    currentLanguage: String,
    languages: List<Language>,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = Orange
            )
        },
        text = {
            LazyColumn {
                items(languages) { language ->
                    LanguageDialogItem(
                        language = language,
                        isSelected = language.code == currentLanguage,
                        onSelect = { onLanguageSelected(language.code) }
                    )
                }
            }
        },
        confirmButton = {},
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
private fun LanguageDialogItem(
    language: Language,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language.flag,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = language.name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) Orange else Gold
            )
        }
        
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Orange,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun AppInfoItem() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Gold
                )
                Text(
                    text = stringResource(R.string.version),
                    style = MaterialTheme.typography.bodySmall,
                    color = Orange2
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(R.string.app_description),
            style = MaterialTheme.typography.bodySmall,
            color = Orange2,
            lineHeight = MaterialTheme.typography.bodySmall.lineHeight * 1.4
        )
    }
}

@Composable
private fun ThemeSettingItem(
    isDarkMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = Gold,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = stringResource(R.string.dark_mode),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Gold
                )
                Text(
                    text = stringResource(R.string.dark_mode_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = Orange2
                )
            }
        }
        
        Switch(
            checked = isDarkMode,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Orange,
                checkedTrackColor = Gold.copy(alpha = 0.5f)
            )
        )
    }
}