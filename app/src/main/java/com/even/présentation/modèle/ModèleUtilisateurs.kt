package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntGetUtilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class ModèleUtilisateurs(
    val intGetUtilisateur : IntGetUtilisateur
) {

    constructor() : this(IntGetUtilisateur(_source))

    companion object {
        lateinit var _source : ISourceDeDonnées
        fun setSource(source : ISourceDeDonnées) {
            _source = source
        }
    }

    /**
     * Méthode qui permet d'aller chercher la liste d'utilisateur qui participe à l'événement.
     *
     * @param idÉvénement Clé unique qui représente l'événement sélectionné.
     * @return Retourne la liste d'utilisateur
     */
    suspend fun getUtilisateursDansÉvénement(idÉvénement : Int) : List<Utilisateur> {
        return intGetUtilisateur.getUtilisateursDansÉvénement(idÉvénement)
    }

    /**
     * Méthode qui permet d'aller chercher l'image de l'utilisateur.
     *
     * @param id Clé unique qui représente l'utilisateur sélectionné.
     * @return Retourne la chaine de caractère de l'url de l'image.
     */
    fun getImageUtilisateur(id : Int) : String {
        return intGetUtilisateur.getImageUtilisateur(id)
    }
}