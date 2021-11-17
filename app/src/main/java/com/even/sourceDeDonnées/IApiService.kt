package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.retrofit.ApiReponse
import retrofit2.http.POST
import retrofit2.Response


import retrofit2.http.*

interface IApiService {
    @POST("register") //www.url.com/" "
    suspend fun creerUtilisateur(@Body post: Utilisateur): Response<ApiReponse>
}