package com.even.domaine.interacteur

import android.util.Log
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.IApiService

import retrofit2.Response

class IntEnregistrement(val api: IApiService) {

    suspend fun enregisterNouvelUtilisateur(
        username: CharSequence,
        password: CharSequence,
        email: CharSequence,
        phone: CharSequence
    ): Response<ApiReponse> {
        val reponseRequete = api.creerUtilisateur(
            username,
            password,
            email,
            phone
        )
        return reponseRequete
    }
}