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
}