package com.br444n.dragonball.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.managers.language.models.Language
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange

@Composable
fun LanguageSelectionDialog(
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
                text = language.displayName,
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