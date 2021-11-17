package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesBidon

class IntGetAllÉvénements(var _source : ISourceDeDonnées) {

    fun getAllÉvénements() : List<Événement> {
        return _source.getAllEvenements()
    }
}