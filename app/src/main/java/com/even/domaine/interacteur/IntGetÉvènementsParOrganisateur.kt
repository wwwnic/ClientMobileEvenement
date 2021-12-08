package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetÉvènementsParOrganisateur(var _source: ISourceDeDonnées) {

    suspend fun demanderMesÉvènements(
        id: Int
    ): List<Événement> {
        val listeEvenement = _source.getEvenementsParOrganisateur(id)
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }.substring(0, 16)
        }
        return listeEvenement
    }
}