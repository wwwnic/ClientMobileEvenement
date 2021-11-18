package com.even.domaine.entité

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import com.even.R
import com.google.gson.annotations.SerializedName
import java.util.*

class Événement(
    val idEvenement: Int,
    @SerializedName("nomEvenement")
    var nom: String,
    var location: String,
    var date: String,
    var idOrganisateur: Int,
    var description: String
) {
    var organisateur : Utilisateur? = null
}