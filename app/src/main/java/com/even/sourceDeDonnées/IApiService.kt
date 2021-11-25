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
    @GET("api/Utilisateur/GetAll")
    suspend fun getAllUtilisateurs() : Response<List<Utilisateur>>
    @GET("/api/Utilisateur/GetById")
    suspend fun getUtilisateurParId(@Query("id") id : Int) : Response<Utilisateur>
    @GET("api/Evenement/GetRecent")
    suspend fun getAllEvenements() : Response<List<Événement>>
    @GET("api/Evenement/GetParRecherche")
    suspend fun getEvenementsParRecherche(@Query("nom") nom : String,
                                          @Query("mois") mois : String,
                                          @Query("location") location : String,
                                          @Query("organisateur") organisateur : String) : Response<List<Événement>>
    @POST("api/Evenement/New")
    suspend fun creerEvenement(@Body evenement : Événement) : Response<Événement>
}