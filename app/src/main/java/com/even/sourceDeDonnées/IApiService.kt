package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ApiReponse
import retrofit2.http.POST
import retrofit2.Response


import retrofit2.http.*
import okhttp3.ResponseBody
import retrofit2.Call
import java.sql.Date


interface IApiService {

    @POST("api/Utilisateur/New")
    suspend fun creerUtilisateur(@Body utilisateur: Utilisateur): Response<Void>

    @GET("api/Utilisateur/GetAll")
    suspend fun getAllUtilisateurs(): Response<List<Utilisateur>>

    @GET("api/Evenement/GetAll")
    suspend fun getAllEvenements(): Response<List<Événement>>

    @GET("/api/Evenement/GetParRecherche")
    suspend fun getEvenementsParRecherche(
        @Query("nom") nom: String,
        @Query("mois") mois: String,
        @Query("location") location: String,
        @Query("organisateur") organisateur: String
    ): Response<List<Événement>>
}