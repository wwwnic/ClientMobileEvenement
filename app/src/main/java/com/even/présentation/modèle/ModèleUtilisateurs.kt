package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntGetAllUtilisateurs
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

    /*fun getUtilisateursDansÉvénement(événement : Événement) : List<Utilisateur> {
        var listeUtilEven = IntGetUtilisateursDansÉvénement(source).getUtilisateursDansÉvénement()
        var listeUtil = ArrayList<Utilisateur>()
        listeUtilEven.filter { it.idEvenement == événement.idEvenement}.forEach { li ->
            listeUtil.add(Utilisateurs.filter { it.idUtilisateur == li.idUtilisateur }.first())
        }
        return listeUtil
    }*/

    fun getImageUtilisateur(id : Int) : String {
        return _source.getImageUtilisateur(id)
    }
}