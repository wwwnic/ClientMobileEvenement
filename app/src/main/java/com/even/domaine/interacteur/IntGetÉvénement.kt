package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetÉvénement(var _source : ISourceDeDonnées) {

    suspend fun getÉvénementParId(id : Int) : Événement? {
        return _source.getÉvenementParId(id)
    }
}