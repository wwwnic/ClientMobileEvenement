package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Constructor
import java.util.*

data class Utilisateur(
    val nomUtilisateur: String,
    val motDePasse: String,
    val courriel: String,
    val telephone: String,
) {
    constructor(
        idUtilisateur: Int,
        nomUtilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String,
        dateCreation: String,
        commentaires: List<Commentaire>?,
        evenements: List<Evenement>?,
        utilisateurevenements: List<Utilisateurevenement>?
    ) : this(nomUtilisateur, motDePasse, courriel, telephone)
}