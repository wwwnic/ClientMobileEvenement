package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class IntGetÉvénement(var _source: ISourceDeDonnées) {

    /**
     * Méthode qui permet d'aller chercher un événement dans la source de donnée à partir d'un id.
     *
     * @param id Clé unique qui représente l'événement à rechercher
     * @return Retourne l'événement trouvé dans la source de donnée
     */
    suspend fun getÉvénementParId(id: Int): Événement? {
        return _source.getÉvénementParId(id)
    }

    /**
     * Méthode qui va chercher tous les événements dans lesquels l'organisateur correspond
     * à l'utilisateur connecté
     *
     * @param idUtilisateur Clé unique qui représente l'utilisateur
     * @return Retourne la liste d'événement
     */
    suspend fun demanderMesÉvènements(
        id: Int
    ): List<Événement> {
        val listeEvenement = _source.getÉvénementsParOrganisateur(id)
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }.substring(0, 16)
        }
        return listeEvenement
    }

    /**
     * Méthode qui va chercher une liste d'événement dans lesquels l'utilisateur est participant.
     *
     * @param idUtilisateur La clé unique qui représente l'utilisateur
     * @return Retourne la liste d'événement
     */
    suspend fun demanderMesParticipations(
        id: Int
    ): List<Événement> {
        val listeEvenement = _source.getÉvénementsParParticipation(id)
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }.substring(0, 16)
        }
        return listeEvenement
    }

    /**
     * Méthode qui permet de faire la recherche d'un événement à partir de plusieurs critère.
     *
     * @param nom Nom de l'événement à rechercher
     * @param mois Mois durant lequel l'événement à rechercher se déroulera
     * @param location L'endroit dans lequel l'événement à rechercher se déroulera
     * @param organisateur Le nom de l'utilisateur qui organise l'événement à rechercher.
     *
     * @return Retourne l'événement trouvé
     */
    suspend fun getÉvénementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        var listeEvenement = _source.getÉvénementsParRecherche(nom, mois, location, organisateur)
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }
        }
        return listeEvenement
    }

    /**
     * Méthode qui permet de faire la recherche de tous les événements présent dans la source de donnée
     *
     * @return Retourne la liste des événements trouvé.
     */
    suspend fun getÉvénementsRécents(): List<Événement> {
        var listeEvenement = _source.getAllEvenements()
        if (listeEvenement.isNullOrEmpty()) listeEvenement = ArrayList<Événement>()
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }.substring(0, 16)
        }
        return listeEvenement
    }

    /**
     * Méthode qui permet d'aller chercher l'image de l'événement.
     *
     * @param id Clé unique qui représente l'événement sélectionné.
     * @return Retourne la chaine de caractère de l'url de l'image.
     */
    fun getImageÉvénement(id: Int): String {
        return _source.getImageEvenement(id)
    }
}