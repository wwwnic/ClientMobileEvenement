package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetÉvènementsParOrganisateur(var _source : ISourceDeDonnées) {

    suspend fun demanderMesÉvènements(
        id: Int
    ): List<Événement> {
        val reponseRequete = _source.getEvenementsParOrganisateur(id)
        return reponseRequete
    }
}