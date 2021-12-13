package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

/**
 * Permets d'interagir avec la source de données
 *
 * @property source La source de données avec laquelle interagir
 */
@testOuvert
class IntConnexion(val source: ISourceDeDonnées) {

    /**
     * Demande un profil à la source de données
     *
     * @param identifiantUtilisateur Un utilisateur contenant le nom et le mot de passe
     * @return Un utilisateur recupéré dans la source de données
     */
    suspend fun connexionDemanderProfil(
        identifiantUtilisateur: Utilisateur
    ): Utilisateur? {
        return source.demanderProfil(identifiantUtilisateur)
    }
}