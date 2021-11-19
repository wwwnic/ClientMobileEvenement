package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.Response


import retrofit2.http.*

interface IApiService {
    @POST("enregistrement")
    suspend fun creerUtilisateur(@Body post: Utilisateur): Response<ApiReponse>
    @GET("/Utilisateurs")
    suspend fun getAllUtilisateurs() : Response<List<Utilisateur>>
    @GET("/Evenements")
    suspend fun getAllEvenements() : Response<List<Événement>>
}