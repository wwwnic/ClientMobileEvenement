package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetAllÉvénements
import com.even.domaine.interacteur.IntGetUtilisateursDansÉvénement
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleÉvénements(val source : ISourceDeDonnées) {
    var Événements : List<Événement> = ArrayList<Événement>()

    init {
        Événements = IntGetAllÉvénements(source).getAllÉvénements()
        Événements.forEach { e ->
            ModèleUtilisateurs(source).Utilisateurs?.forEach { u ->
                if(u.idUtilisateur == e.idOrganisateur) e.organisateur = u}
        }
    }

    fun getÉvénementsParPrésence(utilisateur : Utilisateur) : List<Événement> {
        var listeUtilEven = IntGetUtilisateursDansÉvénement(source).getUtilisateursDansÉvénement()
        var listeEven = ArrayList<Événement>()
        listeUtilEven.filter { it.idUtilisateur == utilisateur.idUtilisateur}.forEach { li ->
            listeEven.add(Événements.filter { it.idEvenement == li.idEvenement }.first())
        }
        return listeEven
    }

    fun getÉvénementsParCréateur(utilisateur: Utilisateur) : List<Événement> {
        return Événements.filter { it.idOrganisateur == utilisateur.idUtilisateur }
    }
}