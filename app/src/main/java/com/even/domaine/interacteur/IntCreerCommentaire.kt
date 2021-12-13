package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

/**
 * Permet d'interagir avec la source de donnée pour aller chercher les informations nécessaire.
 *
 * @property _source La source de données avec laquelle interagir
 */
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