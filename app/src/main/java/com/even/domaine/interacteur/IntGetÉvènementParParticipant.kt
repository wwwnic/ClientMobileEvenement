package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetÉvènementParParticipant(var _source : ISourceDeDonnées) {

    suspend fun enregisterNouvelUtilisateur(
        id: Int
    ): List<Événement> {
        val reponseRequete = _source.getEvenementParParticipation(id)
        return reponseRequete
    }
}