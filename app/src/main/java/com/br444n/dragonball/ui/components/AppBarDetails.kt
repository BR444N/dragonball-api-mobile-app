package com.br444n.dragonball.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange2
import com.br444n.dragonball.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    titleColor: Color = Gold,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    titleFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { 
            Text(
                text = title,
                color = titleColor,
                fontWeight = FontWeight.Bold,
                fontSize = titleFontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            ) 
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Red
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Orange2,
            titleContentColor = Red,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
        )
    )
}
