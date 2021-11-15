package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement

interface ISourceDeDonnées {
    fun getAllUtilisateurs() : List<Utilisateur>
    fun getAllEvenements() : List<Événement>
    fun getUtilisateursEvenement() : List<UtilisateurÉvénement>
}