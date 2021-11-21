package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ApiReponse
import retrofit2.http.POST
import retrofit2.Response


import retrofit2.http.*
import okhttp3.ResponseBody
import retrofit2.Call


interface IApiService {
    @FormUrlEncoded
    @POST("enregistrement")
    suspend fun creerUtilisateur(
        @Field("nomUtilisateur") nomUtilisateur: CharSequence,
        @Field("motDePasse") motDePasse: CharSequence,
        @Field("courriel") courriel: CharSequence,
        @Field("telephone") telephone: CharSequence
    ): Response<ApiReponse>
}