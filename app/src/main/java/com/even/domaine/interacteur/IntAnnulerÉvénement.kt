package com.even.domaine.interacteur

import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response


class IntAnnulerÉvénement(var _source : ISourceDeDonnées) {

    suspend fun annulerÉvénement(id : Int) : Response<Void> {
        return _source.supprimerEvenement(id)
    }
}