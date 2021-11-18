package com.even.domaine.interacteur

import android.util.Log
import com.even.sourceDeDonnées.ApiClient.apiService
import com.even.domaine.entité.Utilisateur

class IntEnregistrement() {

    suspend fun enregisterNouvelUtilisateur(
        username: String,
        password: String,
        email: String,
        phone: String
    ) {
        var reponseRequete = apiService.creerUtilisateur(
            Utilisateur(
                0,
                username,
                password,
                email,
                phone
            )
        )
        Log.i("Réponse POST", reponseRequete.toString()) //TODO: log
    }
}