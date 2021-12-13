package com.even.domaine.interacteur

import com.even.domaine.entité.UtilisateurÉvénement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

/**
 * Permet d'interagir avec la source de donnée pour aller chercher les informations nécessaire.
 *
 * @property _source La source de données avec laquelle interagir
 */
@testOuvert
class IntDétailÉvenement(val _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'ajouter la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun ajouterParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = _source.ajouterParticipation(utilisateurÉvenement)
        return reponseRequête
    }

    /**
     * Méthode qui permet de retirer la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = _source.retirerParticipation(utilisateurÉvenement)
        return reponseRequête
    }

}