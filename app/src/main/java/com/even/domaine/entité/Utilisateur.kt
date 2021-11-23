package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Constructor
import java.util.*

data class Utilisateur(
    val idUtilisateur: Int?,
    val nomUtilisateur: String,
    val motDePasse: String,
    val courriel: String,
    val telephone: String,
    val dateCreation: String?,
    val commentaires: List<Commentaire>?,
    val evenements: List<Événement>?,
    val utilisateurevenements: List<UtilisateurÉvénement>?
) {

    constructor(
        nomUtilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String,
    ) : this(null,nomUtilisateur, motDePasse, courriel, telephone,null,null,null,null)
}