package com.even.domaine.entité

data class Evenement(
    val idEvenement: Long,
    val nomEvenement: String,
    val location: String,
    val date: String,
    val idOrganisateur: Long,
    val description: String,
    val commentaires: List<Any?>,
    val utilisateurevenements: List<Utilisateurevenement>
)