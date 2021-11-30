package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées

class IntGetCommentaires(var _source : ISourceDeDonnées) {
    suspend fun getCommentairesParÉvénement(id : Int) : List<Commentaire> {
        var listeCommentaires = _source.getCommentairesParEvenement(id)
        if (listeCommentaires.isNotEmpty()) {
            listeCommentaires.forEach { commentaire ->
                commentaire.utilisateur = IntGetUtilisateur(_source).getParId(commentaire.idUtilisateur)
                commentaire.dateCommentaire = commentaire.dateCommentaire.split("T").let { it[0] + " " + it[1] }
            }
        }
        return listeCommentaires
    }
}