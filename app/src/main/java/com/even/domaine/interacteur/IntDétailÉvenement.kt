package com.even.domaine.interacteur

import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntDétailÉvenement(val api: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'ajouter la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun ajouterParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = api.ajouterParticipation(utilisateurÉvenement)
        return reponseRequête
    }

    /**
     * Méthode qui permet de retirer la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = api.retirerParticipation(utilisateurÉvenement)
        return reponseRequête
    }

}