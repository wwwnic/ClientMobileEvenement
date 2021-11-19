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
            ModèleUtilisateurs(source).Utilisateurs.forEach { u ->
                if(u.idUtilisateur == e.idOrganisateur) e.organisateur = u}
            e.date = e.date.split("T").let {it.get(0)+" "+it.get(1)}
        }
    }

    fun getÉvénementsParPrésence(utilisateur : Utilisateur) : List<Événement> {
        var listeUtilDansEven = IntGetUtilisateursDansÉvénement(source).getUtilisateursDansÉvénement()
        var listeEvenPresents = ArrayList<Événement>()
        listeUtilDansEven.filter { it.idUtilisateur == utilisateur.idUtilisateur}.forEach { li ->
            listeEvenPresents.add(Événements.filter { it.idEvenement == li.idEvenement }.first())
        }
        return listeEvenPresents
    }

    fun getÉvénementsParCréateur(utilisateur: Utilisateur) : List<Événement> {
        return Événements.filter { it.idOrganisateur == utilisateur.idUtilisateur }
    }
}