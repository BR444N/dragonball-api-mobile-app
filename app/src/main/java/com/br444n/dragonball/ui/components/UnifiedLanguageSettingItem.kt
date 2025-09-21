package com.br444n.dragonball.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.managers.language.models.Language
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange2

@Composable
fun UnifiedLanguageSettingItem(
    currentLanguage: String,
    languages: List<Language>,
    isTranslating: Boolean,
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
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
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Gold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentLang.flag} ${currentLang.displayName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Orange2
                    )
                    if (isTranslating) {
                        Spacer(modifier = Modifier.width(8.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(12.dp),
                            strokeWidth = 1.5.dp,
                            color = Orange2
                        )
                    }
                }
                if (currentLanguage == "en") {
                    Text(
                        text = "Interface & content in English",
                        style = MaterialTheme.typography.bodySmall,
                        color = Orange2.copy(alpha = 0.7f)
                    )
                } else {
                    Text(
                        text = "Interfaz y contenido en español",
                        style = MaterialTheme.typography.bodySmall,
                        color = Orange2.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}