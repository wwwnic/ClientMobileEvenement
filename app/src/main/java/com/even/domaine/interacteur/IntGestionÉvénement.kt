package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntGestionÉvénement(var _source: ISourceDeDonnées) {

    suspend fun creerÉvénement(événement: Événement): Événement? {
        return _source.creerEvenement(événement)
    }

    suspend fun modifierÉvénement(événement: Événement): Response<Void> {
        return _source.modifierEvenement(événement)
    }

    suspend fun annulerÉvénement(id: Int): Response<Void> {
        return _source.supprimerEvenement(id)
    }
}