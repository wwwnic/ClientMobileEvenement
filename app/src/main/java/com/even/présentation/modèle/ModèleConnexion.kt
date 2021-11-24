package com.even.présentation.modèle

import android.util.Log
import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleConnexion(val api: ISourceDeDonnées) {

    var utilisateur: Utilisateur? = null
        get() = field
        set(value) {
            field = value
        }

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Boolean {
        val utilisateurRecuperé =
            IntConnexion(api).connexionDemanderProfil(nomUtilisateur, motDePasse)
        utilisateur = utilisateurRecuperé
        return utilisateurRecuperé?.nomUtilisateur != null
    }
}