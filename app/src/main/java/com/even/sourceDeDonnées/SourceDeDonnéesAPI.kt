package com.even.sourceDeDonnées

import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetAllUtilisateurs
import com.even.sourceDeDonnées.ApiClient.apiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceDeDonnéesAPI : ISourceDeDonnées {
    override fun getAllUtilisateurs(): List<Utilisateur> {
        var reponse = ""
        var liste : List<Utilisateur> = ArrayList<Utilisateur>()
        CoroutineScope(Dispatchers.IO).launch {
           reponse = apiService.getAllUtilisateurs().toString()
        }
        Gson().fromJson<Utilisateur>(reponse, object:TypeToken<List<Utilisateur>>() {}.type)
        return liste
    }

    override fun getAllEvenements(): List<Événement> {
        var liste : List<Événement> = ArrayList<Événement>()
        CoroutineScope(Dispatchers.IO).launch {
            liste = apiService.getAllEvenements().body() as List<Événement>
        }
        return liste
    }

    override fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        TODO("Not yet implemented")
    }

    override fun creerUtilisateur() {
        TODO("Not yet implemented")
    }
}