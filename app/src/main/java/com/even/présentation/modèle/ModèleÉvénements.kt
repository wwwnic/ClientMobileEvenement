package com.even.présentation.modèle

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.*
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

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

    suspend fun setÉvénementPrésenté(id : Int) {
        var événement = intGetÉvénement.getÉvénementParId(id)
        événement!!.organisateur = intGetUtilisateur.getParId(événement.idOrganisateur)
        événement.organisateur!!.urlImage = ModèleUtilisateurs(intGetUtilisateur).getImageUtilisateur(événement.organisateur!!.idUtilisateur!!)
        événement.date = événement.date.split("T").let { it[0] + " " + it[1] }.substring(0,16)
        événementPrésenté = événement
    }

    suspend fun getÉvénementsRécents(): List<Événement> {
        return intGetÉvénement.getÉvénementsRécents()
    }

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

    suspend fun créerÉvénement(événement: Événement): Événement? {
        return intGestionÉvénement.creerÉvénement(événement)
    }

    suspend fun modifierÉvénement(événement: Événement) : Response<Void> {
        return intGestionÉvénement.modifierÉvénement(événement = événement)
    }

    suspend fun annulerÉvénement(id : Int) : Response<Void> {
        return intGestionÉvénement.annulerÉvénement(id)
    }

    suspend fun demanderLesParticipations(idUtilisateur: Int): List<Événement> {
        val participations =
            intGetÉvénement.demanderMesParticipations(idUtilisateur)
        return participations
    }

    suspend fun demanderSesPropreÉvènement(idUtilisateur: Int): List<Événement> {
        val mesÉvènements =
            intGetÉvénement.demanderMesÉvènements(idUtilisateur)
        return mesÉvènements
    }

    suspend fun ajouterParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return intDétailÉvenement.ajouterParticipation(utilisateurÉvénement)
    }

    suspend fun retirerParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return intDétailÉvenement.retirerParticipation(utilisateurÉvénement)
    }

    suspend fun getCommentairesDansÉvénement(id : Int) : List<Commentaire> {
        return intGetCommentaires.getCommentairesParÉvénement(id)
    }

    suspend fun créerCommentaire(commentaire: Commentaire) : Response<Void>
    {
        return intCreerCommentaire.creerCommentaire(commentaire = commentaire)
    }

    fun getImageÉvénement(id: Int): String {
        return intGetÉvénement.getImageÉvénement(id)
    }
}