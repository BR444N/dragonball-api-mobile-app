
package com.br444n.dragonball.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.theme.Orange2
import com.br444n.dragonball.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragonBallAppBar(
    onSettingsClick: () -> Unit,
    onMenuItemClick: (String) -> Unit
) {
    var menuExpand by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.dragonball),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Dragon Explorer", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Orange2,
            titleContentColor = Red
        ),
        actions = {
            Box {
                TooltipBox(
                    positionProvider = object : PopupPositionProvider {
                        override fun calculatePosition(
                            anchorBounds: IntRect,
                            windowSize: IntSize,
                            layoutDirection: LayoutDirection,
                            popupContentSize: IntSize
                        ): IntOffset {
                            val spacingPx = with(density) { 4.dp.toPx().toInt() }
                            val x =
                                anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2
                            val y = anchorBounds.bottom + spacingPx
                            val adjustedX =
                                x.coerceIn(0, windowSize.width - popupContentSize.width)
                            val adjustedY =
                                y.coerceAtMost(windowSize.height - popupContentSize.height)
                            return IntOffset(adjustedX, adjustedY)
                        }
                    },
                    tooltip = {
                        PlainTooltip {
                            Text("Menu")
                        }
                    },
                    state = remember { TooltipState() }
                ) {
                    IconButton(onClick = { menuExpand = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = Red
                        )
                    }
                }
                DropdownMenu(
                    expanded = menuExpand,
                    onDismissRequest = { menuExpand = false },
                    shadowElevation = 10.dp,
                    modifier = Modifier
                        .background(Orange2),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Planets", color = Red) },
                        onClick = { onMenuItemClick("Planets"); menuExpand = false }
                    )
                }
            }
            TooltipBox(
                positionProvider = object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect,
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize
                    ): IntOffset {
                        val spacingPx = with(density) { 4.dp.toPx().toInt() }
                        val x =
                            anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2
                        val y = anchorBounds.bottom + spacingPx
                        val adjustedX =
                            x.coerceIn(0, windowSize.width - popupContentSize.width)
                        val adjustedY =
                            y.coerceAtMost(windowSize.height - popupContentSize.height)
                        return IntOffset(adjustedX, adjustedY)
                    }
                },
                tooltip = {
                    PlainTooltip {
                        Text("Settings")
                    }
                },
                state = remember { TooltipState() }
            ) {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Red
                    )
                }
            }
        }
    )
}
