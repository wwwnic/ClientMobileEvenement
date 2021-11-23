package com.even.domaine.entité

data class Evenement(
    val idEvenement: Int,
    val nomEvenement: String,
    val location: String,
    val date: String,
    val idOrganisateur: Int,
    val description: String,
    val commentaires: List<Any?>,
    val utilisateurevenements: List<Utilisateurevenement>
)