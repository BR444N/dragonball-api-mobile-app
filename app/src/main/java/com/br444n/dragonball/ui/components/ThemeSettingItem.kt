package com.br444n.dragonball.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange
import com.br444n.dragonball.ui.theme.Orange2

@Composable
fun ThemeSettingItem(
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