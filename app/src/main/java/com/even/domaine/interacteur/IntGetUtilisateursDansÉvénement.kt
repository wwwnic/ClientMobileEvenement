package com.even.domaine.interacteur

import com.even.domaine.entité.UtilisateurÉvénement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetUtilisateursDansÉvénement(var _source : ISourceDeDonnées) {
    suspend fun getUtilisateursDansÉvénement() : List<UtilisateurÉvénement> {
        return _source.getUtilisateursEvenement()
    }
}