package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Événement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.Response


import retrofit2.http.*

interface IApiService {
    @POST("enregistrement")
    suspend fun creerUtilisateur(@Body post: Utilisateur): Response<ApiReponse>
    @GET("/api/Utilisateur/GetAll")
    suspend fun getAllUtilisateurs() : Response<List<Utilisateur>>
    @GET("/api/Evenement")
    suspend fun getAllEvenements() : Response<List<Événement>>
}