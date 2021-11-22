package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.util.*

data class Utilisateur(
    //TODO: Enlever l'id de l'utilisateur
    @SerializedName("id")
    val idUtilisateur : Int,
    @SerializedName("username")
    var nomUtilisateur : String,
    @SerializedName("password")
    var motDePasse : String,
    @SerializedName("email")
    var courriel : String,
    @SerializedName("phone")
    var telephone : String,
) {
}