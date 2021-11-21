package com.even.présentation.modèle

import com.even.domaine.entité.ApiClient
import com.even.domaine.entité.ApiReponse
import com.even.domaine.interacteur.IntEnregistrement
import com.even.sourceDeDonnées.IApiService
import retrofit2.Response

class ModèleEnregistrement(val api: IApiService) {

    var responseBodyRequêteEnregistrement: Response<ApiReponse>? = null

    suspend fun effectuerEnregistrement(
        username: CharSequence,
        password: CharSequence,
        email: CharSequence,
        phone: CharSequence
    ): Response<ApiReponse> {
        val reponseBodyRequête = IntEnregistrement(api).enregisterNouvelUtilisateur(
            username,
            password,
            email,
            phone
        )
        responseBodyRequêteEnregistrement = reponseBodyRequête
        return reponseBodyRequête
    }
}