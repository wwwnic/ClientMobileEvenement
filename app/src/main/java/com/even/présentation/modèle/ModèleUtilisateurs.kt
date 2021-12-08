package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntGetAllUtilisateurs
import com.even.domaine.interacteur.IntGetUtilisateursDansÉvénement
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleUtilisateurs {

    companion object {
        lateinit var _source : ISourceDeDonnées
        fun setSource(source : ISourceDeDonnées) {
            _source = source
        }
    }

    suspend fun getUtilisateurs(): List<Utilisateur> {
        return IntGetAllUtilisateurs(_source).getAllUtilisateurs()
    }

    suspend fun getUtilisateursDansÉvénement(idÉvénement : Int) : List<Utilisateur> {
        return IntGetUtilisateursDansÉvénement(_source).getUtilisateursDansÉvénement(idÉvénement)
    }

    fun getImageUtilisateur(id : Int) : String {
        return _source.getImageUtilisateur(id)
    }
}