package com.even.domaine.interacteur

import android.util.Log
import com.even.domaine.entité.ApiClient
import com.even.domaine.entité.ApiClient.apiService
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.IApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class IntEnregistrement() {

    suspend fun enregisterNouvelUtilisateur(
        username: String,
        password: String,
        email: String,
        phone: String
    ): Response<ApiReponse> {
        var reponseRequete = apiService.creerUtilisateur(
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