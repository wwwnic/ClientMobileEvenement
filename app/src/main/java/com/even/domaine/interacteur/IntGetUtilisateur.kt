package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetUtilisateur(var _source : ISourceDeDonnées) {
    suspend fun getParId(id : Int) : Utilisateur? {
        return _source.getUtilisateurParId(id)
    }

    suspend fun getParNom(nom : String) : List<Utilisateur> {
        return _source.getUtilisateursParNom(nom)
    }
}