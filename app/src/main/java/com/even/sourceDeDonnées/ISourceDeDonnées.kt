package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement

interface ISourceDeDonnées {
    suspend fun getAllUtilisateurs() : List<Utilisateur>
    suspend fun getAllEvenements() : List<Événement>
    fun getUtilisateursEvenement() : List<UtilisateurÉvénement>
    fun creerUtilisateur()
}