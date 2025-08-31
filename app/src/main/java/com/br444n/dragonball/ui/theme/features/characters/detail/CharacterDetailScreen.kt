package com.br444n.dragonball.ui.theme.features.characters.detail

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.br444n.dragonball.data.remote.models.Character
import com.br444n.dragonball.ui.components.CharacterDetailAppBar
import com.br444n.dragonball.ui.theme.*
import com.br444n.dragonball.utils.LoadingAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: String,
    onBackClick: (() -> Unit)? = null
) {
    val viewModel: CharacterDetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    Scaffold(
        topBar = {
            CharacterDetailAppBar(
                title = when (uiState) {
                    is CharacterDetailUiState.Success -> (uiState as CharacterDetailUiState.Success).character.name
                    else -> "Character Details"
                },
                onBackClick = onBackClick,
                titleColor = Red
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is CharacterDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }
            is CharacterDetailUiState.Success -> {
                CharacterDetailContent(
                    character = (uiState as CharacterDetailUiState.Success).character,
                    paddingValues = paddingValues
                )
            }
            is CharacterDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Error: ${(uiState as CharacterDetailUiState.Error).message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { viewModel.retry(characterId) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Orange
                                )
                            ) {
                                Text("Retry")
                            }
                            if (onBackClick != null) {
                                OutlinedButton(onClick = onBackClick) {
                                    Text("Go Back")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailContent(
    character: Character,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con imagen de fondo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            // Imagen de fondo con blur
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Gold.copy(alpha = 0.3f), BlendMode.SrcAtop),
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            renderEffect = BlurEffect(
                                25f,
                                25f,
                                TileMode.Decal
                            )
                        }
                        alpha = 0.7f
                    }
            )

            // Gradiente overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = 800f
                        )
                    )
            )

            // Imagen del personaje centrada
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }

        // Contenido de detalles
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre del personaje
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Orange
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cards de información
            CharacterInfoCard(
                title = "Basic Information",
                items = listOf(
                    "Race" to (character.race ?: "Unknown"),
                    "Gender" to (character.gender ?: "Unknown"),
                    "Affiliation" to (character.affiliation ?: "Unknown")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CharacterInfoCard(
                title = "Power Level",
                items = listOf(
                    "Ki" to (character.ki ?: "Unknown"),
                    "Max Ki" to (character.maxKi ?: "Unknown")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            if (!character.description.isNullOrBlank()) {
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
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Orange
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = character.description,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4,
                            color = Gold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CharacterInfoCard(
    title: String,
    items: List<Pair<String, String>>
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
                color = Orange
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            items.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$label:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        color = Gold
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Orange2,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}
