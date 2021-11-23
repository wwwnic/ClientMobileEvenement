package com.even.présentation.modèle

import android.util.Log
import com.even.domaine.entité.ApiClient
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntEnregistrement
import com.even.sourceDeDonnées.IApiService
import retrofit2.Response

class ModèleEnregistrement(val api: IApiService) {

    var responseBodyRequêteEnregistrement: Response<Void>? = null

    suspend fun effectuerEnregistrement(
        Utilisateur: String,
        motDePasse: String,
        courriel: String,
        telephone: String
    ): Response<Void> {
        val utilisateurAEnregistrer =
            Utilisateur(Utilisateur, motDePasse, courriel, telephone)
        val reponseBodyRequête = IntEnregistrement(api).enregisterNouvelUtilisateur(
            utilisateurAEnregistrer
        )
        responseBodyRequêteEnregistrement = reponseBodyRequête
        return reponseBodyRequête
    }
}