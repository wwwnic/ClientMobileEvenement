package com.even.ui.composants

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.even.MainActivity
import com.even.R
import com.even.domaine.entité.Événement
import com.even.ui.fragment.details_evenement
import com.even.ui.fragment.liste_evenement
import java.time.LocalDateTime
import java.util.*

@Composable
fun CarteÉvénement(événement: Événement) {
    Column(modifier = Modifier
        .clip(RectangleShape)
        .padding(all = 8.dp)
        .fillMaxWidth()
        .height(300.dp)
        .background(colors.primaryVariant)
        .clickable { }) {
        Row(modifier = Modifier
            .weight(1F)
            .height(150.dp)) {
            Image(
                painter = painterResource(id = R.drawable.wowimg),
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds
            )
        }
        Column(modifier = Modifier
            .weight(1F)
            .padding(4.dp)) {
            Row {
                Text(
                    text = événement.nom,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = colors.onPrimary
                )
            }
            Row {
                Text(
                    text = événement.location,
                    color = colors.onPrimary
                )
            }
            Row {
                Text(
                    text = événement.date.toString(),
                    fontSize = 20.sp,
                    color = colors.onPrimary
                )
            }
            Row {
                Text(
                    text = "Organisé par: ${événement.organisateur}",
                    color = colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun ListeCarteÉvénements(événements : List<Événement>) {
    LazyColumn {
        items(événements) { e ->
            CarteÉvénement(événement = e)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CarteÉvénementPreview() {
    MaterialTheme {
        CarteÉvénement(Événement("Party chez Bob","Maison de Bob", Calendar.getInstance().time,"bob", R.drawable.wowimg,"gros party chez Bob let's gooooooo!"))
    }
}

