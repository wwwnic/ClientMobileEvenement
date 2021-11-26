package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetUtilisateur(var _source : ISourceDeDonnées) {
    suspend fun getParId(id : Int) : Utilisateur? {
        return _source.getUtilisateurParId(id)
    }

    suspend fun getParNom(nom : String) : Utilisateur? {
        TODO("Not yet implemented")
    }
}