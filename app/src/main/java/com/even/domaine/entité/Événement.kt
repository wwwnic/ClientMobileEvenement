package com.even.domaine.entité

import com.google.gson.annotations.SerializedName

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