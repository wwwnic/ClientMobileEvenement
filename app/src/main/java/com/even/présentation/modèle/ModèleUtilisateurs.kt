package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetAllUtilisateurs
import com.even.domaine.interacteur.IntGetAllÉvénements
import com.even.domaine.interacteur.IntGetUtilisateursDansÉvénement
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleUtilisateurs(val source : ISourceDeDonnées) {
    var Utilisateurs : List<Utilisateur> = ArrayList<Utilisateur>()

    init {
        Utilisateurs = IntGetAllUtilisateurs(source).getAllUtilisateurs()
    }

    fun getUtilisateursDansÉvénement(événement : Événement) : List<Utilisateur> {
        var listeUtilEven = IntGetUtilisateursDansÉvénement(source).getUtilisateursDansÉvénement()
        var listeUtil = ArrayList<Utilisateur>()
        listeUtilEven.filter { it.idEvenement == événement.idEvenement}.forEach { li ->
            listeUtil.add(Utilisateurs.filter { it.idUtilisateur == li.idUtilisateur }.first())
        }
        return listeUtil
    }
}