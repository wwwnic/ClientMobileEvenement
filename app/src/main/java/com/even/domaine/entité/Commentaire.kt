package com.even.domaine.entité

import com.google.gson.annotations.SerializedName
import java.util.*

class Commentaire(
    val idCommentaire: Int?,
    val idEvenement: Int,
    val idUtilisateur: Int,
    @SerializedName("date")
    var dateCommentaire: String?,
    var texte: String
) {
    var utilisateur : Utilisateur? = null

    constructor(
        idEvenement : Int,
        idUtilisateur : Int,
        texte : String
    ) : this(null,idEvenement,idUtilisateur,null,texte)
}