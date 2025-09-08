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

    Scaffold(
        topBar = {
            CharacterDetailAppBar(
                title = "Planets",
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
                        text = "No planets found",
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
            // Imagen del planeta (lado izquierdo)
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(50.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                AsyncImage(
                    model = planet.image,
                    contentDescription = planet.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Contenido del texto (lado derecho)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                // Nombre del planeta
                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Orange2
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Estado del planeta
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (planet.isDestroyed) Icons.Default.Dangerous else Icons.Default.CheckCircle,
                        contentDescription = if (planet.isDestroyed) "Destroyed" else "Active",
                        tint = if (planet.isDestroyed) Red else Green,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (planet.isDestroyed) "Destroyed" else "Active",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (planet.isDestroyed) Red else Green,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Descripción con componente expandible
                if (!planet.description.isNullOrBlank()) {
                    ExpandableText(
                        text = planet.description,
                        isExpanded = isExpanded,
                        onToggleExpanded = { isExpanded = !isExpanded }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // ID del planeta
                Text(
                    text = "Planet ID: ${planet.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
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
        Text(
            text = text,
            color = Gold,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLinesCollapsed,
            overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4
        )
        
        // Mostrar botón solo si el texto es largo
        if (text.length > 150) { // Aproximadamente 3 líneas
            Spacer(modifier = Modifier.height(4.dp))
            
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
                        text = if (isExpanded) "Show less" else "Show more",
                        style = MaterialTheme.typography.bodySmall,
                        color = Orange,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Orange,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
