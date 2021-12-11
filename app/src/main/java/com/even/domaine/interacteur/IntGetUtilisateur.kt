package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class IntGetUtilisateur(var _source: ISourceDeDonnées) {
    suspend fun getParId(id: Int): Utilisateur? {
        return _source.getUtilisateurParId(id)
    }

    suspend fun getUtilisateursDansÉvénement(idEvenement: Int): List<Utilisateur> {
        return _source.getUtilisateursDansEvenement(idEvenement)
    }

    fun getImageUtilisateur(id : Int) : String {
        return _source.getImageUtilisateur(id)
    }
}