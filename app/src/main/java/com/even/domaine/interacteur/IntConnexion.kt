package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntConnexion(val _source: ISourceDeDonnées) {

    suspend fun connexionDemanderProfil(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Utilisateur? {
        return _source.demanderProfil(nomUtilisateur, motDePasse)
    }
}