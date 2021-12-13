package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class IntGetCommentaires(var _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'aller chercher le liste de commentaire relié à un événement
     * à partir de la source de donnée.
     *
     * @param id Clé unique qui représente l'événement sélectionné
     * @return La liste de commentaire de l'événement.
     */
    suspend fun getCommentairesParÉvénement(id: Int): List<Commentaire> {
        var listeCommentaires = _source.getCommentairesParEvenement(id)
        if (listeCommentaires.isNullOrEmpty()) listeCommentaires = ArrayList<Commentaire>()
        listeCommentaires.forEach { commentaire ->
            commentaire.utilisateur =
                IntGetUtilisateur(_source).getParId(commentaire.idUtilisateur)
            commentaire.dateCommentaire =
                commentaire.dateCommentaire!!.split("T").let { it[0] + " " + it[1] }
        }
        return listeCommentaires
    }
}