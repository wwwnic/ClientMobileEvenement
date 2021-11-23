package com.even.domaine.entité

data class Utilisateur(
    val idUtilisateur: Int?,
    var nomUtilisateur: String,
    var motDePasse: String,
    var courriel: String,
    var telephone: String,
    var dateCreation: String?

) {
    constructor(
        nomUtilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String,
    ) : this(null, nomUtilisateur, motDePasse, courriel, telephone, null)
}