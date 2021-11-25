package com.even.domaine.entité

import com.google.gson.annotations.SerializedName

class Commentaire(
    val idCommentaire : Int,
    val idEvenement : Int,
    val idUtilisateur : Int,
    @SerializedName("date")
    val dateCommentaire : String,
    var texte : String
) {
}