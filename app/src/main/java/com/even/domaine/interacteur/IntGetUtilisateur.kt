package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

/**
 * Permet d'interagir avec la source de donnée pour aller chercher les informations nécessaire.
 *
 * @property _source La source de données avec laquelle interagir
 */
@testOuvert
class IntGetUtilisateur(var _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'aller chercher un utilisateur à partir d'un id.
     *
     * @param id Clé unique qui réprésente l'utilisateur à rechercher.
     * @return Retourne l'utilisateur qui correspons à l'id.
     */
    suspend fun getParId(id: Int): Utilisateur? {
        return _source.getUtilisateurParId(id)
    }

    /**
     * Méthode qui permet d'aller chercher la liste d'utilisateur qui participe à un événement.
     *
     * @param idEvenement Clé unique qui représente l'événement sélectionné.
     * @return Retourne la liste d'utilisateur.
     */
    suspend fun getUtilisateursDansÉvénement(idEvenement: Int): List<Utilisateur> {
        return _source.getUtilisateursDansEvenement(idEvenement)
    }

    /**
     * Méthode qui permet d'aller chercher l'image de l'utilisateur.
     *
     * @param id Clé unique qui représente l'utilisateur sélectionné.
     * @return Retourne la chaine de caractère de l'url de l'image.
     */
    fun getImageUtilisateur(id : Int) : String {
        return _source.getImageUtilisateur(id)
    }
}