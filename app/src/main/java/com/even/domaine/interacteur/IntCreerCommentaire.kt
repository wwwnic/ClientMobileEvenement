package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntCreerCommentaire(var _source: ISourceDeDonnées) {
    suspend fun creerCommentaire(commentaire: Commentaire): Response<Void> {
        return _source.creerCommentaire(commentaire = commentaire)
    }
}