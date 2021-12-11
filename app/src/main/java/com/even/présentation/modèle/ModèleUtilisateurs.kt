package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntGetUtilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class ModèleUtilisateurs(
    val intGetUtilisateur : IntGetUtilisateur
) {

    constructor() : this(IntGetUtilisateur(_source))

    companion object {
        lateinit var _source : ISourceDeDonnées
        fun setSource(source : ISourceDeDonnées) {
            _source = source
        }
    }

    suspend fun getUtilisateursDansÉvénement(idÉvénement : Int) : List<Utilisateur> {
        return intGetUtilisateur.getUtilisateursDansÉvénement(idÉvénement)
    }

    fun getImageUtilisateur(id : Int) : String {
        return intGetUtilisateur.getImageUtilisateur(id)
    }
}