package com.even.présentation.modèle

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.*
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

/**
 * Permet d'aller chercher les informations nécessaire dans l'intéracteur.
 * Garde aussi une instance de l'événement sélectionné en mémoire.
 *
 * @property intGetÉvénement Un intéracteur pour aller chercher un événement.
 * @property intGestionÉvénement Un intéracteur pour faire la gestion d'un événement
 * @property intDétailÉvenement Un intéracteur pour faire la gestion des détails d'un événement
 * @property intGetCommentaires Un intéracteur pour aller chercher les commentaire d'un événement
 * @property intCreerCommentaire Un intérateur pour ajouter des commentaires
 * @property intGetUtilisateur Un intéracteur pour aller chercher un utilisateur
 */
@testOuvert
class ModèleÉvénements(
    val intGetÉvénement : IntGetÉvénement,
    val intGestionÉvénement: IntGestionÉvénement,
    val intDétailÉvenement: IntDétailÉvenement,
    val intGetCommentaires: IntGetCommentaires,
    val intCreerCommentaire: IntCreerCommentaire,
    val intGetUtilisateur: IntGetUtilisateur
) {

    constructor() : this(
        IntGetÉvénement(_source),
        IntGestionÉvénement(_source),
        IntDétailÉvenement(_source),
        IntGetCommentaires(_source),
        IntCreerCommentaire(_source),
        IntGetUtilisateur(_source)
    )

    companion object {
        lateinit var _source: ISourceDeDonnées
        var événementPrésenté: Événement? = null
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
    }

    /**
     * Méthode qui permet de sauvegarder les informations de l'événement directement dans le modèle
     * pour pouvoir y accéder facilement dans les présentateurs
     *
     * @param id Clé unique qui représente l'événement à garder en mémoire.
     */
    suspend fun setÉvénementPrésenté(id : Int) {
        var événement = intGetÉvénement.getÉvénementParId(id)
        événement!!.organisateur = intGetUtilisateur.getParId(événement.idOrganisateur)
        événement.organisateur!!.urlImage = ModèleUtilisateurs(intGetUtilisateur).getImageUtilisateur(événement.organisateur!!.idUtilisateur!!)
        événement.date = événement.date.split("T").let { it[0] + " " + it[1] }.substring(0,16)
        événementPrésenté = événement
    }

    /**
     * Méthode qui permet de faire la recherche de tous les événements présent dans la source de donnée
     *
     * @return Retourne la liste des événements trouvé.
     */
    suspend fun getÉvénementsRécents(): List<Événement> {
        return intGetÉvénement.getÉvénementsRécents()
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
        return intGetÉvénement.getÉvénementsParRecherche(
            nom,
            mois,
            location,
            organisateur
        )
    }

    /**
     * Méthode qui permet de passer un événement afin de l'ajouter à la source de donnée.
     *
     * @param événement L'événement qui doit être ajouté.
     * @return ???
     */
    suspend fun créerÉvénement(événement: Événement): Événement? {
        return intGestionÉvénement.creerÉvénement(événement)
    }

    /**
     * Méthode qui permet de faire la modification d'un événement dans la source de donnée.
     *
     * @param événement L'événement modifié qui devra remplacer l'événement existant
     * dans la source de donnée.
     * @return Une réponse vide de l'api
     */
    suspend fun modifierÉvénement(événement: Événement) : Response<Void> {
        return intGestionÉvénement.modifierÉvénement(événement = événement)
    }

    /**
     * Méthode qui permet de retirer un événement de la source de donnée.
     *
     * @param id Clé unique qui représente l'événement retirer.
     * @return Une réponse vide de l'api
     */
    suspend fun annulerÉvénement(id : Int) : Response<Void> {
        return intGestionÉvénement.annulerÉvénement(id)
    }

    /**
     * Méthode qui va chercher une liste d'événement dans lesquel l'utilisateur est participant.
     *
     * @param idUtilisateur La clé unique qui représente l'utilisateur
     * @return Retourne la liste d'événement
     */
    suspend fun demanderLesParticipations(idUtilisateur: Int): List<Événement> {
        val participations =
            intGetÉvénement.demanderMesParticipations(idUtilisateur)
        return participations
    }

    /**
     * Méthode qui va chercher tous les événements dans lesquel l'organisateur correspond
     * à l'utilisateur connecté
     *
     * @param idUtilisateur Clé unique qui représente l'utilisateur
     * @return Retourne la liste d'événement
     */
    suspend fun demanderSesPropreÉvènement(idUtilisateur: Int): List<Événement> {
        val mesÉvènements =
            intGetÉvénement.demanderMesÉvènements(idUtilisateur)
        return mesÉvènements
    }

    /**
     * Méthode qui permet d'ajouter la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun ajouterParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return intDétailÉvenement.ajouterParticipation(utilisateurÉvénement)
    }

    /**
     * Méthode qui permet de retirer la participation d'un utilisateur à un événement.
     *
     * @param utilisateurÉvénement Objet composé de l'id de l'utilisateur et de l'événement
     * @return Réponse vide de l'api
     */
    suspend fun retirerParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return intDétailÉvenement.retirerParticipation(utilisateurÉvénement)
    }

    /**
     * Méthode qui permet d'aller chercher la liste de commentaire relié à un événement.
     *
     * @param id Clé unique qui représente l'événement sélectionné.
     * @return Retourne la liste de commentaire pour l'événement.
     */
    suspend fun getCommentairesDansÉvénement(id : Int) : List<Commentaire> {
        return intGetCommentaires.getCommentairesParÉvénement(id)
    }

    /**
     * Méthode qui permet d'ajouter un commentaire à l'événement sélectionné.
     *
     * @param commentaire Un objet commentaire
     * @return Réponse vide de l'api
     */
    suspend fun créerCommentaire(commentaire: Commentaire) : Response<Void>
    {
        return intCreerCommentaire.creerCommentaire(commentaire = commentaire)
    }

    /**
     * Méthode qui permet d'aller chercher l'image de l'événement.
     *
     * @param id Clé unique qui représente l'événement sélectionné.
     * @return Retourne la chaine de caractère de l'url de l'image.
     */
    fun getImageÉvénement(id: Int): String {
        return intGetÉvénement.getImageÉvénement(id)
    }
}