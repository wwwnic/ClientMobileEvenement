package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntGestionÉvénement(var _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'ajouter un événement dans la source de donnée.
     *
     * @param événement L'événement à ajouter
     * @return ???
     */
    suspend fun creerÉvénement(événement: Événement): Événement? {
        return _source.creerEvenement(événement)
    }

    /**
     * Méthode qui permet de modfier un événement existant.
     *
     * @param événement L'événement qui a été modifié
     * @return Réponse vide de l'api
     */
    suspend fun modifierÉvénement(événement: Événement): Response<Void> {
        return _source.modifierEvenement(événement)
    }

    /**
     * Méthode qui permet de retirer un événement de la source de donnée.
     *
     * @param id Clé unique qui représente l'événement à retirer.
     * @return Réponse vide de l'api
     */
    suspend fun annulerÉvénement(id: Int): Response<Void> {
        return _source.supprimerEvenement(id)
    }
}