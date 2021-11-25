package com.even.présentation.modèle

import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetÉvènementParParticipant
import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleMesÉvènements {
    companion object {
        lateinit var _source: ISourceDeDonnées
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
    }

    suspend fun demanderLesParticipationsParId(id: Int): List<Événement> {
        val participations = IntGetÉvènementParParticipant(_source).enregisterNouvelUtilisateur(id)
        return participations
    }


}