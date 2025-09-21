package com.br444n.dragonball.ui.theme.features.characters

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.br444n.dragonball.data.remote.models.Character
import androidx.compose.ui.res.stringResource
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.components.DragonBallAppBar
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange
import com.br444n.dragonball.ui.theme.Orange2

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CharacterListScreen(
    characters: List<Character>,
    onSettingsClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    onCharacterClick: (String) -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            DragonBallAppBar(
                onSettingsClick = onSettingsClick,
                onMenuItemClick = onMenuItemClick
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
            horizontalArrangement = Arrangement.Center
        ) {
            items(characters.size) { index ->
                val character = characters[index]
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CharacterCardFullScreen(
                        character = character,
                        onClick = { onCharacterClick(character.id.toString()) }
                    )
                }
            }
        }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCardFullScreen(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
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
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Gold, BlendMode.SrcAtop),
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                renderEffect = BlurEffect(
                                    30f,
                                    30f,
                                    TileMode.Decal
                                )
                            }
                            alpha = 0.9f
                        }
                )
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
                color = Orange,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${stringResource(R.string.race)}: ${character.race ?: stringResource(R.string.unknown)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Orange2,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${stringResource(R.string.affiliation)}: ${character.affiliation ?: stringResource(R.string.unknown)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center,
                color = Gold,
                fontWeight = FontWeight.Normal
            )
        }
    }
}