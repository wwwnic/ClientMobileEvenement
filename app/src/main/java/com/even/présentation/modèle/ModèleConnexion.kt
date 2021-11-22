package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.sourceDeDonnées.IApiService
import com.google.gson.Gson

class ModèleConnexion(val api: IApiService) {

    var utilisateur: Utilisateur? = null

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Boolean {
        val reponseApi = IntConnexion(api).connexionDemanderProfil(nomUtilisateur, motDePasse)
        val estRequêteComplète = reponseApi.isSuccessful
        if (estRequêteComplète) {
            val gson = Gson()
            val infoUtilisateur = gson.fromJson(reponseApi.body()!!.data, Utilisateur::class.java)
            utilisateur = infoUtilisateur
        }
        return estRequêteComplète
    }
}