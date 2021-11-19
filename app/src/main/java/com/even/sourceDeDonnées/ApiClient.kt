package com.even.sourceDeDonnées

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val URL: String =
        // Changer pour l'adresse du service ou du postman
        //"https://40d1c99b-3ca7-43af-8c31-c5f4554f0fcc.mock.pstmn.io"
        "https://77ee61d4-ccf3-4da0-861e-baa339b0257e.mock.pstmn.io/"

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: IApiService by lazy {
        retrofit.create(IApiService::class.java)
    }
}