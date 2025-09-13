package com.br444n.dragonball.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.br444n.dragonball.data.remote.models.Transformation
import com.br444n.dragonball.ui.theme.Gold
import com.br444n.dragonball.ui.theme.Orange

@Composable
fun TransformationCard(transformation: Transformation) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de la transformación
            AsyncImage(
                model = transformation.image,
                contentDescription = transformation.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Información de la transformación con tamaño dinámico
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nombre de la transformación con tamaño dinámico
                Text(
                    text = transformation.name,
                    style = when {
                        transformation.name.length <= 12 -> MaterialTheme.typography.bodyLarge
                        transformation.name.length <= 20 -> MaterialTheme.typography.bodyMedium
                        transformation.name.length <= 30 -> MaterialTheme.typography.bodySmall
                        else -> MaterialTheme.typography.labelMedium
                    },
                    fontWeight = FontWeight.Bold,
                    color = Orange,
                    textAlign = TextAlign.Center,
                    maxLines = if (transformation.name.length > 20) 3 else 2,
                    lineHeight = when {
                        transformation.name.length <= 12 -> MaterialTheme.typography.bodyLarge.lineHeight
                        transformation.name.length <= 20 -> MaterialTheme.typography.bodyMedium.lineHeight
                        transformation.name.length <= 30 -> MaterialTheme.typography.bodySmall.lineHeight
                        else -> MaterialTheme.typography.labelMedium.lineHeight
                    }
                )
                
                // Ki level si está disponible
                if (!transformation.ki.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ki: ${transformation.ki}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gold,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}