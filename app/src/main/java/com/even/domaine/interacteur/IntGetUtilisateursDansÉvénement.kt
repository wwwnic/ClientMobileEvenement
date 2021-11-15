package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetUtilisateursDansÉvénement(var _source : ISourceDeDonnées) {
    fun getUtilisateursDansÉvénement() : List<UtilisateurÉvénement> {
        return _source.getUtilisateursEvenement()
    }
}