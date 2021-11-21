package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.util.*

data class Utilisateur(
    val idUtilisateur : Int,
    var nomUtilisateur : String,
    var motDePasse : String,
    var courriel : String,
    var telephone : String,
) {
}