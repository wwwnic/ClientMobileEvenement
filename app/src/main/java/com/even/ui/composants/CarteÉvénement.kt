package com.even.ui.composants

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.even.R
import com.even.domaine.entité.Événement

@Composable
fun CarteÉvénement(événement: Événement,clickEvent: () -> Unit) {
    Column(modifier = Modifier
        .clickable(onClick = clickEvent)
        .clip(RectangleShape)
        .padding(all = 8.dp)
        .fillMaxWidth()
        .height(300.dp)) {
        Row(modifier = Modifier
            .height(150.dp)) {
            Image(
                painter = painterResource(id = R.drawable.wowimg),
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds
            )
        }
        Column(modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)) {
            Row {
                Text(
                    text = événement.nom,
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
                    text = événement.date.toString(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row {
                Text(
                    text = "Organisé par: ${événement.organisateur?.nomUtilisateur}",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun ListeCarteÉvénements(événements: List<Événement>,clickEvent : (Événement) -> Unit) {
    LazyColumn {
        items(événements) { e ->
            CarteÉvénement(événement = e,clickEvent = { clickEvent(e) })
        }
    }
}
