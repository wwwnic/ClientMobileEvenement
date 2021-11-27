package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import retrofit2.Response
import retrofit2.http.*

interface IApiService {

    @POST("api/Utilisateur/New")
    suspend fun creerUtilisateur(@Body utilisateur: Utilisateur): Response<Void>

    @GET("api/Utilisateur/GetAll")
    suspend fun getAllUtilisateurs(): Response<List<Utilisateur>>

    @GET("api/Evenement/GetAll")
    suspend fun getAllEvenements(): Response<List<Événement>>

    @GET("/api/Utilisateur/GetById")
    suspend fun getUtilisateurParId(@Query("id") id: Int): Response<Utilisateur>

    @GET("/api/Evenement/GetParRecherche")
    suspend fun getEvenementsParRecherche(
        @Query("nom") nom: String,
        @Query("mois") mois: String,
        @Query("location") location: String,
        @Query("organisateur") organisateur: String
    ): Response<List<Événement>>

    @POST("api/Evenement/New")
    suspend fun creerEvenement(@Body evenement: Événement): Response<Événement>

    @POST("/api/Utilisateur/Login")
    suspend fun demanderProfil(
        @Body utilisateur: Utilisateur
    ): Response<Utilisateur?>

    @GET("/api/Evenement/GetParParticipant/{id}")
    suspend fun getEvenementsParParticipation(@Path("id") id: Int): Response<List<Événement>>

    @GET("/api/Evenement/GetParOrganisateur/{id}")
    suspend fun getEvenementsParOrganisateur(@Path("id") id: Int): Response<List<Événement>>

    @GET("/api/Evenement/{id}")
    suspend fun getEvenementParId(@Path("id") id : Int) : Response<Événement>
}