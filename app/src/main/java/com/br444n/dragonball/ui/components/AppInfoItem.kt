package com.br444n.dragonball.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange2

@Composable
fun AppInfoItem() {
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