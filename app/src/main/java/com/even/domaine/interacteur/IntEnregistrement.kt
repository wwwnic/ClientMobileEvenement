package com.even.domaine.interacteur

import android.util.Log
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.IApiService

import retrofit2.Response

class IntEnregistrement(val api : IApiService) {

    suspend fun enregisterNouvelUtilisateur(
        username: String,
        password: String,
        email: String,
        phone: String
    ): Response<ApiReponse> {
        var reponseRequete = api.creerUtilisateur(
            Utilisateur(
                0,
                username,
                password,
                email,
                phone
            )
        )
        Log.i("Réponse POST", reponseRequete.body().toString()) //TODO: log
        return reponseRequete
    }
}