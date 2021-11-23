package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.IApiService
import retrofit2.Response

class IntEnregistrement(val api: IApiService) {

    suspend fun enregisterNouvelUtilisateur(
        utilisateur : Utilisateur
    ): Response<Void> {
        val reponseRequete = api.creerUtilisateur(utilisateur)
        return reponseRequete
    }
}