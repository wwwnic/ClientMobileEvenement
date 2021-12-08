package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class IntModifierÉvénement(var _source: ISourceDeDonnées) {

    suspend fun modifierÉvénement(événement: Événement): Response<Void> {
        return _source.modifierEvenement(événement)
    }
}