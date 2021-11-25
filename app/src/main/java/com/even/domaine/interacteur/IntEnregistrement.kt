package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class IntEnregistrement(val api: ISourceDeDonnées) {

    suspend fun enregisterNouvelUtilisateur(
        utilisateur: Utilisateur
    ): Response<Void> {
        val reponseRequete = api.creerUtilisateur(utilisateur)
        return reponseRequete
    }
}