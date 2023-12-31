package com.even.ui.composants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.even.R
import coil.compose.rememberImagePainter
import com.even.domaine.entité.Événement

/**
 * Affichage d'un événement sous forme de carte.
 */
@Composable
fun CarteÉvénement(événement: Événement, clickEvent: () -> Unit, imageUrl: (Int) -> String) {
    var urlImg = imageUrl(événement.idEvenement)
    Column(
        modifier = Modifier
            .clickable(onClick = clickEvent)
            .clip(RectangleShape)
            .border(4.dp, figmaMauve, RoundedCornerShape(6.dp))
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RectangleShape)
        ) {
            Image(
                painter = rememberImagePainter(if (urlImg.contains("http")) urlImg else urlImg.toInt()),
                contentDescription = "Image",
                modifier = Modifier
                    .graphicsLayer { scaleX = 2.5F;scaleY = 1.3F;clip = true; }
                    .fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        ) {
            Row {
                Text(
                    text = événement.nomEvenement,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row {
                Text(
                    text = événement.location,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row {
                Text(
                    text = événement.date,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row {
                Text(
                    text =  "${LocalContext.current.resources.getString(R.string.organizedBy)} ${événement.organisateur?.nomUtilisateur}",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun ListeCarteÉvénements(
    événements: List<Événement>,
    clickEvent: (Événement) -> Unit,
    imageUrl: (Int) -> String
) {
    LazyColumn {
        items(événements) { e ->
            CarteÉvénement(
                événement = e,
                clickEvent = { clickEvent(e) },
                { imageUrl(e.idEvenement) })
        }
    }
}
