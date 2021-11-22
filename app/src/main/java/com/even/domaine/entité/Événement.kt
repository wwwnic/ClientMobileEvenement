package com.even.domaine.entité

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import com.even.R
import java.util.*

class Événement(
    val idEvenement: Int,
    var nom: String,
    var location: String,
    var date: Date,
    var idOrganisateur: Int,
    var image: Int,
    var description: String,
    var nbParticipant : Int
) {
    var organisateur : Utilisateur? = null
}