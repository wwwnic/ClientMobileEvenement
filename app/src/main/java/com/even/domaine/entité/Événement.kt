package com.even.domaine.entité

class Événement(
    val idEvenement: Int,
    var nomEvenement: String,
    var location: String,
    var date: String,
    var idOrganisateur: Int,
    var description: String
) {
    var organisateur : Utilisateur? = null
}