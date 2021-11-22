package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.util.*

data class Utilisateur(
    val idUtilisateur: Long,
    val nomUtilisateur: String,
    val motDePasse: String,
    val courriel: String,
    val telephone: String,
    val dateCreation: String,
    val commentaires: List<Commentaire>,
    val evenements: List<Evenement>,
    val utilisateurevenements: List<Utilisateurevenement>
)