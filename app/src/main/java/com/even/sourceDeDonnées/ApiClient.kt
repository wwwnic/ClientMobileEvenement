package com.even.sourceDeDonnées

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val URL: String =
        // Changer pour l'adresse du service ou du postman
        //"https://77ee61d4-ccf3-4da0-861e-baa339b0257e.mock.pstmn.io/"
        "http://10.0.0.149:23784/"

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(3,TimeUnit.SECONDS)
            .readTimeout(3,TimeUnit.SECONDS)
            .build()
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