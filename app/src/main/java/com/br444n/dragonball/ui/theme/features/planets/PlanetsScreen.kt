package com.br444n.dragonball.ui.theme.features.planets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.br444n.dragonball.data.remote.models.Planet
import androidx.compose.ui.res.stringResource
import com.br444n.dragonball.R
import com.br444n.dragonball.managers.language.UnifiedLanguageManager
import com.br444n.dragonball.ui.theme.*
import com.br444n.dragonball.utils.LoadingAnimation
import com.br444n.dragonball.ui.components.CharacterDetailAppBar
import com.br444n.dragonball.ui.components.ErrorUiState
import com.br444n.dragonball.ui.components.NoInternetConnectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetsScreen(
    onBackClick: (() -> Unit)? = null
) {
    val viewModel: PlanetsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val currentLanguage by UnifiedLanguageManager.currentLanguage.collectAsState()
    
    // Recargar cuando cambie el idioma unificado
    LaunchedEffect(currentLanguage) {
        viewModel.reloadWithCurrentLanguage()
    }

    Scaffold(
        topBar = {
            CharacterDetailAppBar(
                title = stringResource(R.string.planets),
                onBackClick = onBackClick,
                titleColor = Red,
                contentColor = Red
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is PlanetUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }
            is PlanetUiState.Success -> {
                PlanetsList(
                    planets = (uiState as PlanetUiState.Success).planets,
                    paddingValues = paddingValues
                )
            }
            is PlanetUiState.Error -> {
                ErrorUiState(
                    errorMessage = (uiState as PlanetUiState.Error).message,
                    onRetryClick = { viewModel.retry() },
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is PlanetUiState.NoInternetConnection -> {
                NoInternetConnectionState(
                    onRefreshClick = { viewModel.retry() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is PlanetUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_planets_found),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun PlanetsList(
    planets: List<Planet>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(planets) { planet ->
            PlanetCard(planet = planet)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanetCard(planet: Planet) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PlanetImage(
                imageUrl = planet.image,
                planetName = planet.name
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            PlanetContent(
                planet = planet,
                isExpanded = isExpanded,
                onToggleExpanded = { isExpanded = !isExpanded },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ExpandableText(
    text: String,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    maxLinesCollapsed: Int = 3
) {
    Column {
        ExpandableTextContent(
            text = text,
            isExpanded = isExpanded,
            maxLinesCollapsed = maxLinesCollapsed
        )
        
        if (shouldShowToggleButton(text)) {
            Spacer(modifier = Modifier.height(4.dp))
            ExpandToggleButton(
                isExpanded = isExpanded,
                onToggleExpanded = onToggleExpanded
            )
        }
    }
}

@Composable
private fun ExpandableTextContent(
    text: String,
    isExpanded: Boolean,
    maxLinesCollapsed: Int
) {
    Text(
        text = text,
        color = Gold,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLinesCollapsed,
        overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4
    )
}

@Composable
private fun ExpandToggleButton(
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    TextButton(
        onClick = onToggleExpanded,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
                style = MaterialTheme.typography.bodySmall,
                color = Orange,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) stringResource(R.string.collapse) else stringResource(R.string.expand),
                tint = Orange,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private fun shouldShowToggleButton(text: String): Boolean {
    return text.length > 150 // Aproximadamente 3 líneas
}

@Composable
private fun PlanetImage(
    imageUrl: String?,
    planetName: String
) {
    Card(
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = planetName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun PlanetContent(
    planet: Planet,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        PlanetTitle(planet.name)
        Spacer(modifier = Modifier.height(8.dp))
        
        PlanetStatus(planet.isDestroyed)
        Spacer(modifier = Modifier.height(12.dp))
        
        PlanetDescription(
            description = planet.description,
            isExpanded = isExpanded,
            onToggleExpanded = onToggleExpanded
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PlanetId(planet.id)
    }
}

@Composable
private fun PlanetTitle(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = Orange2
    )
}

@Composable
private fun PlanetStatus(isDestroyed: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = if (isDestroyed) Icons.Default.Dangerous else Icons.Default.CheckCircle,
            contentDescription = if (isDestroyed) stringResource(R.string.destroyed) else stringResource(R.string.active),
            tint = if (isDestroyed) Red else Green,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = if (isDestroyed) stringResource(R.string.destroyed) else stringResource(R.string.active),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDestroyed) Red else Green,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PlanetDescription(
    description: String?,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    if (!description.isNullOrBlank()) {
        ExpandableText(
            text = description,
            isExpanded = isExpanded,
            onToggleExpanded = onToggleExpanded
        )
    }
}

@Composable
private fun PlanetId(id: Int) {
    Text(
        text = "${stringResource(R.string.planet_id)}: $id",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}
