package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesBidon

class IntGetÉvénementsRécents(var _source : ISourceDeDonnées) {

    fun getAllÉvénements() : List<Événement> {
        var liste = _source.getAllEvenements()
        liste.forEach { e ->
            IntGetAllUtilisateurs(_source).getAllUtilisateurs().forEach { u ->
                if(u.idUtilisateur == e.idOrganisateur) e.organisateur = u}
            e.date = e.date.split("T").let {it.get(0)+" "+it.get(1)}
        }
        return liste
    }
}