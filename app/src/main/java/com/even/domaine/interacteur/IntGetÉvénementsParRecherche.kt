package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetÉvénementsParRecherche(var _source: ISourceDeDonnées) {
    suspend fun getÉvénementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        var listeEvenement = _source.getEvenementsParRecherche(nom, mois, location, organisateur)
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }
        }
        return listeEvenement
    }
}