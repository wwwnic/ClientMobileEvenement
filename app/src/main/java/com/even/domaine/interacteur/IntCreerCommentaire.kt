package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class IntCreerCommentaire(var _source : ISourceDeDonnées) {
    suspend fun CreerCommentaire(commentaire: Commentaire) : Response<Void> {
        return _source.creerCommentaire(commentaire = commentaire)
    }
}