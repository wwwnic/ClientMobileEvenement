package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ApiReponse
import retrofit2.http.POST
import retrofit2.Response


import retrofit2.http.*

interface IApiService {
    @POST("enregistrement")
    suspend fun creerUtilisateur(@Body post: Utilisateur): Response<ApiReponse>
}