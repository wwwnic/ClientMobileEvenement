package com.even.sourceDeDonnées

import com.even.domaine.entité.ApiReponse
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ISourceDeDonnées {
    fun getAllUtilisateurs() : List<Utilisateur>
    fun getAllEvenements() : List<Événement>
    fun getUtilisateursEvenement() : List<UtilisateurÉvénement>
    fun creerUtilisateur()
}