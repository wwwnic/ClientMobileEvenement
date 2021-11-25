package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.util.*

class Événement(
    val idEvenement: Int,
    var nomEvenement: String,
    var location: String,
    var date: String,
    var idOrganisateur: Int,
    var description: String
) {
    var organisateur : Utilisateur? = null

    fun setOrganisateura(u : Utilisateur) {
        organisateur = u
    }
}