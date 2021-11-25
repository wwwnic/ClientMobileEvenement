package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntCreerÉvénement(var _source : ISourceDeDonnées) {

    suspend fun creerÉvénement(événement : Événement) : Événement? {
        return _source.creerEvenement(événement)
    }
}