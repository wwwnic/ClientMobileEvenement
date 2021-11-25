package com.even.présentation.modèle

import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntCreerÉvénement
import com.even.domaine.interacteur.IntGetUtilisateur
import com.even.domaine.interacteur.IntGetÉvénementsParRecherche
import com.even.domaine.interacteur.IntGetÉvénementsRécents
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleÉvénements {

    companion object {
        lateinit var _source : ISourceDeDonnées
        var newÉvénement : Événement? = null
        fun setSource(source : ISourceDeDonnées) {
            _source = source
        }
        suspend fun setNouvelÉvénement(événement: Événement) {
            événement.organisateur = IntGetUtilisateur(_source).getParId(événement.idOrganisateur)
            newÉvénement = événement
        }
    }

    suspend fun getÉvénementsRécents() : List<Événement> {
        return IntGetÉvénementsRécents(_source).getAllÉvénements()
    }

    suspend fun getÉvénementsParRecherche(nom : String,mois : String,location : String,organisateur : String) : List<Événement> {
        return IntGetÉvénementsParRecherche(_source).getÉvénementsParRecherche(nom,mois,location,organisateur)
    }

    suspend fun créerÉvénement(événement : Événement) : Événement? {
        return IntCreerÉvénement(_source).creerÉvénement(événement)
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

    fun getImageÉvénement(id : Int) : String {
        return _source.getImageEvenement(id)
    }
}