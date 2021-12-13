package com.even.sourceDeDonnées

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ApiClient.apiService
import retrofit2.Response

class SourceDeDonnéesAPI(val serviceApi: IApiService ) : ISourceDeDonnées {
    constructor() : this(apiService)

    /**
     * Méthode qui permet de faire une requête à l'api pour aller cherhcer la liste complète des
     * utilisateur dans la BD.
     *
     * @return La liste des utilisateurs
     */
    override suspend fun getAllUtilisateurs(): List<Utilisateur> {
        var liste: List<Utilisateur> = ArrayList<Utilisateur>()

        var reponseApi = apiService.getAllUtilisateurs()
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Utilisateur>
        }
        return liste
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste complète des
     * événement dans la BD.
     *
     * @return La liste des événements
     */
    override suspend fun getAllEvenements(): List<Événement> {
        var liste: List<Événement> = ArrayList<Événement>()

        var reponseApi = apiService.getAllEvenements()
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Événement>
        }
        return liste
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller la liste d'utilisateur qui participe
     * à un événement.
     *
     * @param idEvenement Le ID de l'événement sélectionné
     * @return La liste de participant à l'événement qui a le même ID que celui passé en paramètre.
     */
    override suspend fun getUtilisateursDansEvenement(idEvenement: Int): List<Utilisateur> {
        var liste: List<Utilisateur> = ArrayList<Utilisateur>()

        var reponseApi = apiService.getUtilisateursDansEvenement(idEvenement = idEvenement)
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Utilisateur>
        }
        return liste
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvelle utilisateur.
     *
     * @param utilisateur Le nouvel utilisateur à ajouter à la BD
     * @return Réponse vide de l'api
     */
    override suspend fun creerUtilisateur(utilisateur: Utilisateur): Response<Void> {
        return apiService.creerUtilisateur(utilisateur)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour vérifier le login d'un utilisateur.
     *
     * @param utilisateur Les informations nécéssaire pour la connexion.
     * @return L'utilisateur qui correspond aux information de connexion.
     */
    override suspend fun demanderProfil(identifiantUtilisateur: Utilisateur): Utilisateur? {
        var reponseApi = serviceApi.demanderProfil(identifiantUtilisateur)
        return reponseApi.body()
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement à partir
     * des critères de sélection.
     *
     * @param nom Le nom de l'événement
     * @param mois Le mois dans lequel l'événement se déroule
     * @param location L'endroit dans lequel l'événement se déroule
     * @param organisateur L'organiateur de l'événement
     * @return Une liste d'événement qui correspond au critère de sélection.
     */
    override suspend fun getÉvénementsParRecherche(nom : String, mois : String, location : String, organisateur : String): List<Événement> {
        var liste : List<Événement> = ArrayList<Événement>()

        var reponseApi = apiService.getEvenementsParRecherche(nom, mois, location, organisateur)
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Événement>
        }
        return liste
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste de commentaire
     * pour un ID d'événement passé en paramètre.
     *
     * @param id ID de l'événement
     * @return Liste de commentaire
     */
    override suspend fun getCommentairesParEvenement(id: Int): List<Commentaire> {
        var liste : List<Commentaire> = ArrayList<Commentaire>()

        var reponseApi = apiService.getCommentairesParEvenement(id)
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Commentaire>
        }
        return liste
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un commentaire.
     *
     * @param commentaire Commentaire à ajouter.
     * @return Réponse vide de l'api
     */
    override suspend fun creerCommentaire(commentaire: Commentaire): Response<Void> {
        return apiService.creerCommentaire(commentaire)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui correspond
     * au ID passé en paramètre
     *
     * @param id ID de l'événement à aller chercher.
     * @return L'événement qui a le même ID que celui en paramètre.
     */
    override suspend fun getÉvénementParId(id: Int): Événement {
        var evenement: Événement? = null

        var reponseApi = apiService.getEvenementParId(id)
        if (reponseApi.isSuccessful) {
             evenement = reponseApi.body() as Événement
        }
        return evenement!!
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    override suspend fun ajouterParticipation(utilisateurÉvenement: UtilisateurÉvénement) : Response<Void>{
        return apiService.ajouterParticipation(utilisateurÉvenement)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour retirer la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    override suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement) : Response<Void> {
        return apiService.retirerParticipation(utilisateurÉvenement)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvel événement dans la BD
     *
     * @param evenement L'événement à ajouter dans la BD
     * @return Retourne l'événement créer
     */
    override suspend fun creerEvenement(evenement : Événement): Événement? {
        var reponseApi = apiService.creerEvenement(evenement = evenement)
        var newEvenement : Événement? = null
        if (reponseApi.isSuccessful) {
            newEvenement = reponseApi.body() as Événement
        }
        return newEvenement
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour modifier un événement existant.
     *
     * @param evenement L'événement modifié
     * @return Réponse vide de l'api
     */
    override suspend fun modifierEvenement(evenement: Événement): Response<Void> {
        return apiService.updateEvenement(evenement = evenement)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour supprimer un événement de la BD.
     *
     * @param id L'ID de l'événement à supprimer
     * @return Réponse vide de l'api
     */
    override suspend fun supprimerEvenement(id: Int): Response<Void> {
        return apiService.deleteEvenement(id)
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui a
     * le même ID que celui passé en paramètre.
     *
     * @param id Le ID de l'événement à aller chercher.
     * @return L'événement qui correspond au ID en paramètre.
     */
    override suspend fun getUtilisateurParId(id: Int): Utilisateur? {
        var reponseApi = apiService.getUtilisateurParId(id)
        var utilisateur : Utilisateur? = null
        if (reponseApi.isSuccessful) {
            utilisateur = reponseApi.body() as Utilisateur
        }
        return utilisateur
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller un utilisateur qui correspond au
     * nom passé en paramètre.
     *
     * @param nom Le nom de l'utilisateur à rechercher.
     * @return L'utilisateur qui a le même nom que celui passé en paramètre.
     */
    override suspend fun getUtilisateursParNom(nom: String): List<Utilisateur> {
        var reponseApi = apiService.getUtilisateursParNom(nom)
        var utilisateurs : List<Utilisateur> = ArrayList<Utilisateur>()
        if (reponseApi.isSuccessful) {
            utilisateurs = reponseApi.body() as List<Utilisateur>
        }
        return utilisateurs
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * dans lesquels l'ID de l'utilisateur passé en paramètre est participant.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    override suspend fun getÉvénementsParParticipation(id: Int): List<Événement> {
        val reponseApi = apiService.getEvenementsParParticipation(id)
        return if(reponseApi.isSuccessful) reponseApi.body()!! else listOf()
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * qui contient tous les événements dans lesquels l'ID passé en paramètre est l'organisateur.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    override suspend fun getÉvénementsParOrganisateur(id: Int): List<Événement> {
        val reponseApi = apiService.getEvenementsParOrganisateur(id)
        return if(reponseApi.isSuccessful) reponseApi.body()!! else listOf()    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher l'url de l'image
     * d'un utilisateur
     *
     * @param id Id de l'utilisateur
     * @return Retourne l'url de l'image
     */
    override fun getImageUtilisateur(id: Int): String {
        return "http://140.82.8.101/images/utilisateurs/${id}.jpg"
    }

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher l'url de l'image
     * d'un événement
     *
     * @param id Id de l'événement
     * @return Retourne l'url de l'image
     */
    override fun getImageEvenement(id: Int): String {
        return "http://140.82.8.101/images/evenements/${id}.jpg"
    }
}