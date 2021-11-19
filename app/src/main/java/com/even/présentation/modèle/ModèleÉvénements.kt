package com.even.présentation.modèle

import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetÉvénementsRécents
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleÉvénements {

    companion object {
        lateinit var _source : ISourceDeDonnées
        fun setSource(source : ISourceDeDonnées) {
            _source = source
        }
    }

    fun getÉvénementsRécents() : List<Événement> {
        return IntGetÉvénementsRécents(_source).getAllÉvénements()
    }

    /*fun getÉvénementsParPrésence(utilisateur : Utilisateur) : List<Événement> {
        var listeUtilDansEven = IntGetUtilisateursDansÉvénement(source).getUtilisateursDansÉvénement()
        var listeEvenPresents = ArrayList<Événement>()
        listeUtilDansEven.filter { it.idUtilisateur == utilisateur.idUtilisateur}.forEach { li ->
            listeEvenPresents.add(Événements.filter { it.idEvenement == li.idEvenement }.first())
        }
        return listeEvenPresents
    }*/

    /*fun getÉvénementsParCréateur(utilisateur: Utilisateur) : List<Événement> {
        return Événements.filter { it.idOrganisateur == utilisateur.idUtilisateur }
    }*/
}