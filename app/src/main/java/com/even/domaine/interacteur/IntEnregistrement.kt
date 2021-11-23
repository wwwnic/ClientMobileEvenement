package com.even.domaine.interacteur

import android.util.Log
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.IApiService

import retrofit2.Response
import retrofit2.http.PATCH

class IntEnregistrement(val api: IApiService) {

    suspend fun enregisterNouvelUtilisateur(
        utilisateurAEnregistrer: Utilisateur
    ): Response<Void> {
        val reponseRequete = api.creerUtilisateur(utilisateurAEnregistrer)
        return reponseRequete
    }
}