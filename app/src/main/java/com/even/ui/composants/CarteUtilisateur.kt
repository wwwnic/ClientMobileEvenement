package com.even.ui.composants

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.even.domaine.entité.Utilisateur

/**
 * Affichage d'un utilisateur avec son nom et avatar.
 */
@Composable
fun CarteUtilisateur(utilisateur: Utilisateur, imageUrl: (Int) -> String) {
    var urlImg = imageUrl(utilisateur.idUtilisateur!!)
    Row(
        Modifier
            .clip(RectangleShape)
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .border(4.dp, figmaMauve, CircleShape)
    ) {
        Column(
            Modifier
                .width(100.dp)
                .fillMaxHeight()
                .clip(RectangleShape)
                .padding(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(if (urlImg.contains("http")) urlImg else urlImg.toInt()),//painterResource(id = R.drawable.imageutilisateurbidon)
                contentDescription = "Image",
                Modifier
                    .clip(CircleShape)
                    .graphicsLayer { clip = true; }
                    .fillMaxSize()

            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                Text(
                    utilisateur.nomUtilisateur!!, fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(vertical = 25.dp, horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ListeCarteUtilisateurs(utilisateurs: List<Utilisateur>, imageUrl: (Int) -> String) {
    LazyColumn {
        items(utilisateurs) { u ->
            CarteUtilisateur(utilisateur = u, { imageUrl(u.idUtilisateur!!) })
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun Preview() {
    val util = Utilisateur(
        1,
        "BobB",
        "pw123",
        "bob@gmail.com",
        "(514)123-4567",
        SimpleDateFormat("yyyy.MM").format(Date())
    )
    MaterialTheme() {
        CarteUtilisateur(utilisateur = util)
    }
}*/