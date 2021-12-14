package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

/**
 * Permets d'interagir avec la source de données
 *
 * @property source La source de données avec laquelle interagir
 */
@testOuvert
class IntEnregistrement(val source: ISourceDeDonnées) {

    /**
     * Permets d'enregistrer un utilisateur dans la source de données
     *
     * @param utilisateur l'utilisateur à enregistrer
     * @return la reponde de la source de données
     */
    suspend fun enregisterNouvelUtilisateur(
        utilisateur: Utilisateur
    ): Response<Void> {
        val reponseRequete = source.creerUtilisateur(utilisateur)
        return reponseRequete
    }
}