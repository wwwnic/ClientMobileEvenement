package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntConnexion(val api: ISourceDeDonnées) {

    suspend fun connexionDemanderProfil(
        identifiantUtilisateur: Utilisateur
    ): Utilisateur? {
        return api.demanderProfil(identifiantUtilisateur)
    }
}