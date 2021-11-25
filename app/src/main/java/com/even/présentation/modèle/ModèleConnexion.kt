package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleConnexion() {

    companion object {
        lateinit var _source: ISourceDeDonnées
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
    }

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
            IntConnexion(_source).connexionDemanderProfil(nomUtilisateur, motDePasse)
        utilisateur = utilisateurRecuperé
        return utilisateurRecuperé?.nomUtilisateur != null
    }
}