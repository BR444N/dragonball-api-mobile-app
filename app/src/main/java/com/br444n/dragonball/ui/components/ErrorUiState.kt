package com.br444n.dragonball.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.br444n.dragonball.R
import com.br444n.dragonball.ui.theme.*

@Composable
fun ErrorUiState(
    errorMessage: String,
    onRetryClick: () -> Unit,
    onBackClick: (() -> Unit)? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.frieza),
                contentDescription = "Error Image",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Error: $errorMessage",
                color = Orange,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onRetryClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange2
                    )
                ) {
                    Text(
                        "Retry",
                        color = Red
                    )
                }
                
                if (onBackClick != null) {
                    OutlinedButton(
                        onClick = onBackClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Orange2
                        ),
                        border = BorderStroke(width = 1.dp, color = Orange2)
                    ) {
                        Text(
                            "Go Back",
                            color = Red
                        )
                    }
                }
            }
        }
    }
}

