package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntEnregistrement(val api: ISourceDeDonnées) {

    suspend fun enregisterNouvelUtilisateur(
        utilisateur: Utilisateur
    ): Response<Void> {
        val reponseRequete = api.creerUtilisateur(utilisateur)
        return reponseRequete
    }
}