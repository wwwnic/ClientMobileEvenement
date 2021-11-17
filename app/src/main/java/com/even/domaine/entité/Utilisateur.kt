package com.even.domaine.entité

import java.util.*

class Utilisateur(
    val idUtilisateur : Int,
    var nomUtilisateur : String,
    var motDePasse : String,
    var courriel : String,
    var telephone : String,
    var dateCreation : Date
) {
}