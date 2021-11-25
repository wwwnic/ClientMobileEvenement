package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleConnexion() {

    companion object {
        lateinit var _source: ISourceDeDonnées
        var _utilisateurConnecté : Utilisateur? = null
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
        fun setUtilisateurConnecté(utilisateur : Utilisateur?) {
            _utilisateurConnecté = utilisateur
        }
    }

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Boolean {
        val utilisateurRecuperé =
            IntConnexion(_source).connexionDemanderProfil(nomUtilisateur, motDePasse)
        setUtilisateurConnecté(utilisateurRecuperé)
        return utilisateurRecuperé?.nomUtilisateur != null
    }
}