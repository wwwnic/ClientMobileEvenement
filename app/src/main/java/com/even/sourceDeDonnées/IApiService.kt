package com.even.sourceDeDonnées

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.testOuvert
import retrofit2.Response
import retrofit2.http.*

@testOuvert
interface IApiService {

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvelle utilisateur.
     *
     * @param utilisateur Le nouvel utilisateur à ajouter à la BD
     * @return Réponse vide de l'api
     */
    @POST("api/Utilisateur/New")
    suspend fun creerUtilisateur(@Body utilisateur: Utilisateur): Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller cherhcer la liste complète des
     * utilisateur dans la BD.
     *
     * @return La liste des utilisateurs
     */
    @GET("api/Utilisateur/GetAll")
    suspend fun getAllUtilisateurs(): Response<List<Utilisateur>>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste complète des
     * événement dans la BD.
     *
     * @return La liste des événements
     */
    @GET("api/Evenement/GetAll")
    suspend fun getAllEvenements(): Response<List<Événement>>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui a
     * le même ID que celui passé en paramètre.
     *
     * @param id Le ID de l'événement à aller chercher.
     * @return L'événement qui correspond au ID en paramètre.
     */
    @GET("api/Utilisateur/GetById")
    suspend fun getUtilisateurParId(@Query("id") id: Int): Response<Utilisateur>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller un utilisateur qui correspond au
     * nom passé en paramètre.
     *
     * @param nom Le nom de l'utilisateur à rechercher.
     * @return L'utilisateur qui a le même nom que celui passé en paramètre.
     */
    @GET("api/Utilisateur/GetByName")
    suspend fun getUtilisateursParNom(@Query("name") nom : String): Response<List<Utilisateur>>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller la liste d'utilisateur qui participe
     * à un événement.
     *
     * @param idEvenement Le ID de l'événement sélectionné
     * @return La liste de participant à l'événement qui a le même ID que celui passé en paramètre.
     */
    @GET("api/Utilisateur/GetByEvent")
    suspend fun getUtilisateursDansEvenement(@Query("idEvenement")idEvenement : Int) : Response<List<Utilisateur>>

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
    @GET("api/Evenement/GetParRecherche")
    suspend fun getEvenementsParRecherche(
        @Query("nom") nom: String,
        @Query("mois") mois: String,
        @Query("location") location: String,
        @Query("organisateur") organisateur: String
    ): Response<List<Événement>>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un nouvel événement dans la BD
     *
     * @param evenement L'événement à ajouter dans la BD
     * @return Retourne l'événement créer
     */
    @POST("api/Evenement/New")
    suspend fun creerEvenement(@Body evenement: Événement): Response<Événement>

    /**
     * Méthode qui permet de faire une requête à l'api pour modifier un événement existant.
     *
     * @param evenement L'événement modifié
     * @return Réponse vide de l'api
     */
    @PUT("api/Evenement/Update")
    suspend fun updateEvenement(@Body evenement: Événement): Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour supprimer un événement de la BD.
     *
     * @param id L'ID de l'événement à supprimer
     * @return Réponse vide de l'api
     */
    @DELETE("api/Evenement/Delete/secret")
    suspend fun deleteEvenement(@Query ("id")id : Int): Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour vérifier le login d'un utilisateur.
     *
     * @param utilisateur Les informations nécéssaire pour la connexion.
     * @return L'utilisateur qui correspond aux information de connexion.
     */
    @POST("api/Utilisateur/Login")
    suspend fun demanderProfil(
        @Body utilisateur: Utilisateur
    ): Response<Utilisateur?>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * dans lesquels l'ID de l'utilisateur passé en paramètre est participant.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    @GET("api/Evenement/GetParParticipant/{id}")
    suspend fun getEvenementsParParticipation(@Path("id") id: Int): Response<List<Événement>>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher une liste d'événement
     * qui contient tous les événements dans lesquels l'ID passé en paramètre est l'organisateur.
     *
     * @param id Id de l'utilisateur
     * @return Liste d'événement
     */
    @GET("api/Evenement/GetParOrganisateur/{id}")
    suspend fun getEvenementsParOrganisateur(@Path("id") id: Int): Response<List<Événement>>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher un événement qui correspond
     * au ID passé en paramètre
     *
     * @param id ID de l'événement à aller chercher.
     * @return L'événement qui a le même ID que celui en paramètre.
     */
    @GET("/api/Evenement/GetById")
    suspend fun getEvenementParId(@Query("id") id : Int) : Response<Événement>

    /**
     * Méthode qui permet de faire une requête à l'api pour aller chercher la liste de commentaire
     * pour un ID d'événement passé en paramètre.
     *
     * @param id ID de l'événement
     * @return Liste de commentaire
     */
    @GET("/api/Commentaire/GetByEvenement")
    suspend fun getCommentairesParEvenement(@Query("id") id : Int) : Response<List<Commentaire>>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter un commentaire.
     *
     * @param commentaire Commentaire à ajouter.
     * @return Réponse vide de l'api
     */
    @POST("api/Commentaire/New")
    suspend fun creerCommentaire(@Body commentaire : Commentaire) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour ajouter la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    @POST("/api/Utilisateur/addParticipation")
    suspend fun ajouterParticipation(@Body utilisateurÉvenement : UtilisateurÉvénement) : Response<Void>

    /**
     * Méthode qui permet de faire une requête à l'api pour retirer la participation d'un utilisateur
     * dans un événement.
     *
     * @param utilisateurÉvenement Objet qui a comme clé unique le Id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    @POST("/api/Utilisateur/deleteParticipation")
    suspend fun retirerParticipation(@Body utilisateurÉvenement: UtilisateurÉvénement) : Response<Void>
}