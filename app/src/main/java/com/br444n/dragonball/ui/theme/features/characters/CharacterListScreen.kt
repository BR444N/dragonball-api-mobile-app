package com.br444n.dragonball.ui.theme.features.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.br444n.dragonball.data.remote.models.Character

@Composable
fun CharacterListScreen(viewModel: CharactersViewModel = viewModel()) {
    val state = viewModel.uiState.collectAsState().value

    when (state) {
        is CharacterUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CharacterUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.characters) { character ->
                    CharacterItem(character)
                }
            }
        }
        is CharacterUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}")
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(character.name, style = MaterialTheme.typography.titleLarge)
                Text(character.race ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(character.affiliation ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}