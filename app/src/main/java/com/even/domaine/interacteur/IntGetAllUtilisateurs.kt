
package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetAllUtilisateurs(var _source : ISourceDeDonnées) {

    fun getAllUtilisateurs() : List<Utilisateur> {
        return _source.getAllUtilisateurs()
    }
}