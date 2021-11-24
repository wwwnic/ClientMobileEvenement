package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleConnexion(val api: ISourceDeDonnées) {

    var utilisateur: Utilisateur? = null

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Boolean {
        val utilisateur = IntConnexion(api).connexionDemanderProfil(nomUtilisateur, motDePasse)
        return utilisateur?.nomUtilisateur != null
    }
}