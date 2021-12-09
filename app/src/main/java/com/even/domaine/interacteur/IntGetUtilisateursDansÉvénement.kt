package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetUtilisateursDansÉvénement(var _source: ISourceDeDonnées) {
    suspend fun getUtilisateursDansÉvénement(idEvenement: Int): List<Utilisateur> {
        return _source.getUtilisateursDansEvenement(idEvenement)
    }
}