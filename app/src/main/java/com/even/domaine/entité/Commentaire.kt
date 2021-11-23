package com.even.domaine.entité

data class Commentaire(
    val idCommentaire: Long,
    val idEvenement: Long,
    val idUtilisateur: Long,
    val date: String,
    val texte: String,
    val idEvenementNavigation: Événement
)
