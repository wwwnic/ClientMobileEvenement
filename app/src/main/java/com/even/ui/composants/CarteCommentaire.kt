package com.even.ui.composants

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import java.text.SimpleDateFormat
import java.util.*


/**
 * Affichage sous forme de carte d'un commentaire.
 *
 */
@Composable
fun CarteCommentaire(commentaire: Commentaire) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
        Color.Transparent
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = surfaceColor,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .border(4.dp, figmaMauve, RoundedCornerShape(6.dp))
            .animateContentSize()
    ) {
        Column(
            Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp)
        ) {
            Text(
                commentaire.utilisateur!!.nomUtilisateur!!, fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
            Text(
                commentaire.dateCommentaire!!, fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.width(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    commentaire.texte, fontSize = 18.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Affichage d'une liste de carte commentaires.
 */
@Composable
fun ListeCarteCommentaires(commentaires: List<Commentaire>) {
    LazyColumn {
        items(commentaires) { c ->
            CarteCommentaire(commentaire = c)
        }
    }
}

@Preview(showBackground = true)
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
    val comm = Commentaire(
        1,
        1,
        1,
        "2021-11-29 18:00",
        "hey saluthey saluthey saluthey saluthey saluthey saluthey salut" +
                "hey saluthey saluthey saluthey saluthey saluthey saluthey salut" +
                "hey saluthey saluthey saluthey saluthey salut"
    )
    comm.utilisateur = util
    MaterialTheme {
        CarteCommentaire(commentaire = comm)
    }
}