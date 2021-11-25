package com.even.domaine.entité

data class Utilisateur(
    var idUtilisateur: Int?,
    var nomUtilisateur: String,
    var motDePasse: String,
    var courriel: String?,
    var telephone: String?,
    var dateCreation: String?,
    var commentaires: List<Commentaire>?,
    var evenements: List<Événement>?,
    var utilisateurevenements: List<UtilisateurÉvénement>?
) {
    constructor(
        idUtilisateur: Int?,
        nomUtilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String,
        dateCreation: String?
    ) : this(null, nomUtilisateur, motDePasse, courriel, telephone, dateCreation, null, null, null)


    constructor(
        nomUtilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String,
    ) : this(null, nomUtilisateur, motDePasse, courriel, telephone, null, null, null, null)

    constructor(
        nomUtilisateur: String,
        motDePasse: String,
    ) : this(null, nomUtilisateur, motDePasse, null, null, null, null, null, null)

}