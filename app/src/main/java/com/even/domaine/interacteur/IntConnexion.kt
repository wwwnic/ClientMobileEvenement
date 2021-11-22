package com.even.domaine.interacteur

import com.even.domaine.entité.ApiReponse
import com.even.sourceDeDonnées.IApiService
import retrofit2.Response

class IntConnexion(val api: IApiService) {

    suspend fun connexionDemanderProfil(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Response<ApiReponse> {
        return api.demanderProfil(nomUtilisateur, motDePasse)
    }
}