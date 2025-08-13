package com.br444n.dragonball.ui.theme.features.characters

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.br444n.dragonball.R
import com.br444n.dragonball.data.remote.models.Character
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange2
import com.br444n.dragonball.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CharacterListScreen(
    characters: List<Character>,
    onSettingsClick: () -> Unit,
    onMenuItemClick: (String) -> Unit
) {
    var menuExpand by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
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
                            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
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
                                text = { Text("Characters", color = Red) },
                                onClick = { onMenuItemClick("characters"); menuExpand = false }
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.CenterHorizontally),
                                color = Red,
                                thickness = 1.dp,
                            )
                            DropdownMenuItem(
                                text = { Text("Planets", color = Red) },
                                onClick = { onMenuItemClick("Planets"); menuExpand = false }
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.CenterHorizontally),
                                color = Red,
                                thickness = 1.dp,
                            )
                            DropdownMenuItem(
                                text = { Text("Transformations", color = Red) },
                                onClick = { onMenuItemClick("Transformations"); menuExpand = false }
                            )
                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
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
    ) { paddingValues ->

        // LazyRow con snap para que muestre 1 card por pantalla
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            horizontalArrangement = Arrangement.Center
        ) {
            items(characters.size) { index ->
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()  // El card ocupará toda la pantalla
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CharacterCardFullScreen(character = characters[index])
                }
            }
        }
    }
}

@Composable
fun CharacterCardFullScreen(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // 1. Imagen de fondo para el glow (difuminada y coloreada)
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Gold, BlendMode.SrcAtop),
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            // Efecto de blur (requiere Compose 1.6+)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                renderEffect = BlurEffect(
                                    30f,  // Radio de blur
                                    30f,
                                    TileMode.Decal
                                )
                            }
                            alpha = 0.9f // Intensidad del glow
                        }
                )
                //2. Imagen original (encima del glow)
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Race: ${character.race ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Affiliation: ${character.affiliation ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}