package com.even.sourceDeDonnées

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val URL: String =
        "http://140.82.8.101"

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5,TimeUnit.SECONDS)
            .readTimeout(5,TimeUnit.SECONDS)
            //https://stackoverflow.com/questions/41078866/retrofit2-authorization-global-interceptor-for-access-token
            .addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("ApiKey","e159d3d9-cbfe-44c4-8699-c75c8ae30cc9").build()
                chain.proceed(request = request)
            }.build()
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