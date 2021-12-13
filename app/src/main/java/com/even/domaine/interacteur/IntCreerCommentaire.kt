package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntCreerCommentaire(var _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'ajouter un commentaire dans la source de donnée
     *
     * @param commentaire Objet commentaire à ajouter.
     * @return Réponse vide de l'api.
     */
    suspend fun creerCommentaire(commentaire: Commentaire): Response<Void> {
        return _source.creerCommentaire(commentaire = commentaire)
    }
}