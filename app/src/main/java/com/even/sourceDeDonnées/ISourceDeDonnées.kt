package com.even.sourceDeDonnées

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import retrofit2.Response

interface ISourceDeDonnées {

    /**
     * Méthode qui permet de faire une requête à l'api pour aller cherhcer la liste complète des
     * utilisateur dans la BD.
     *
     * @return La liste des utilisateurs
     */
    suspend fun getAllUtilisateurs(): List<Utilisateur>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste complète des
     * événement dans la BD.
     *
     * @return La liste des événements
     */
    suspend fun getAllEvenements(): List<Événement>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui correspond
     * au ID passé en paramètre
     *
     * @param id ID de l'événement à aller chercher.
     * @return L'événement qui a le même ID que celui en paramètre.
     */
    suspend fun getÉvénementParId(id : Int): Événement?

    /**
     * Méthode qui permet de faire une requête à l'api pour aller la liste d'utilisateur qui participe
     * à un événement.
     *
     * @param idEvenement Le ID de l'événement sélectionné
     * @return La liste de participant à l'événement qui a le même ID que celui passé en paramètre.
     */
    suspend fun getUtilisateursDansEvenement(idEvenement : Int) : List<Utilisateur>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvelle utilisateur.
     *
     * @param utilisateur Le nouvel utilisateur à ajouter à la BD
     * @return Réponse vide de l'api
     */
    suspend fun creerUtilisateur(utilisateur: Utilisateur): Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour vérifier le login d'un utilisateur.
     *
     * @param utilisateur Les informations nécéssaire pour la connexion.
     * @return L'utilisateur qui correspond aux information de connexion.
     */
    suspend fun demanderProfil(utilisateur: Utilisateur): Utilisateur?

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvel événement dans la BD
     *
     * @param evenement L'événement à ajouter dans la BD
     * @return Retourne l'événement créer
     */
    suspend fun creerEvenement(evenement: Événement): Événement?

    /**
     * Méthode qui permet de faire une requête à l'api pour modifier un événement existant.
     *
     * @param evenement L'événement modifié
     * @return Réponse vide de l'api
     */
    suspend fun modifierEvenement(evenement: Événement) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour supprimer un événement de la BD.
     *
     * @param id L'ID de l'événement à supprimer
     * @return Réponse vide de l'api
     */
    suspend fun supprimerEvenement(id : Int) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui a
     * le même ID que celui passé en paramètre.
     *
     * @param id Le ID de l'événement à aller chercher.
     * @return L'événement qui correspond au ID en paramètre.
     */
    suspend fun getUtilisateurParId(id: Int): Utilisateur?

    /**
     * Méthode qui permet de faire une requête à l'api pour aller un utilisateur qui correspond au
     * nom passé en paramètre.
     *
     * @param nom Le nom de l'utilisateur à rechercher.
     * @return L'utilisateur qui a le même nom que celui passé en paramètre.
     */
    suspend fun getUtilisateursParNom(nom : String) : List<Utilisateur>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * dans lesquels l'ID de l'utilisateur passé en paramètre est participant.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    suspend fun getÉvénementsParParticipation(id: Int): List<Événement>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * qui contient tous les événements dans lesquels l'ID passé en paramètre est l'organisateur.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    suspend fun getÉvénementsParOrganisateur(id: Int): List<Événement>

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
    suspend fun getÉvénementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste de commentaire
     * pour un ID d'événement passé en paramètre.
     *
     * @param id ID de l'événement
     * @return Liste de commentaire
     */
    suspend fun getCommentairesParEvenement(id : Int) : List<Commentaire>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un commentaire.
     *
     * @param commentaire Commentaire à ajouter.
     * @return Réponse vide de l'api
     */
    suspend fun creerCommentaire(commentaire: Commentaire) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun ajouterParticipation(utilisateurÉvenement : UtilisateurÉvénement) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour retirer la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher l'url de l'image
     * d'un utilisateur
     *
     * @param id Id de l'utilisateur
     * @return Retourne l'url de l'image
     */
    fun getImageUtilisateur(id: Int): String

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher l'url de l'image
     * d'un événement
     *
     * @param id Id de l'événement
     * @return Retourne l'url de l'image
     */
    fun getImageEvenement(id: Int): String
}