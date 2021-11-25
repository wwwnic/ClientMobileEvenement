package com.even.domaine.entité

data class Commentaire(
    val idCommentaire : Int,
    val idEvenement : Int,
    val idUtilisateur : Int,
    val date : String,
    var texte : String
)