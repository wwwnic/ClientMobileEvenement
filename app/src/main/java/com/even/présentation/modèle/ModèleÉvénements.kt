package com.even.présentation.modèle

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.*
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class ModèleÉvénements {

    companion object {
        lateinit var _source: ISourceDeDonnées
        var événementPrésenté: Événement? = null
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
        suspend fun setÉvénementPrésenté(id : Int) {
            var événement = IntGetÉvénement(_source).getÉvénementParId(id)
            événement!!.organisateur = IntGetUtilisateur(_source).getParId(événement.idOrganisateur)
            événement.organisateur!!.urlImage = ModèleUtilisateurs().getImageUtilisateur(événement.organisateur!!.idUtilisateur!!)
            événement.date = événement.date.split("T").let { it[0] + " " + it[1] }.substring(0,16)
            événementPrésenté = événement
        }
    }

    suspend fun getÉvénementsRécents(): List<Événement> {
        return IntGetÉvénementsRécents(_source).getAllÉvénements()
    }

    suspend fun getÉvénementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        return IntGetÉvénementsParRecherche(_source).getÉvénementsParRecherche(
            nom,
            mois,
            location,
            organisateur
        )
    }

    suspend fun créerÉvénement(événement: Événement): Événement? {
        return IntCreerÉvénement(_source).creerÉvénement(événement)
    }

    suspend fun modifierÉvénement(événement: Événement) : Response<Void> {
        return IntModifierÉvénement(_source).modifierÉvénement(événement = événement)
    }

    suspend fun annulerÉvénement(id : Int) : Response<Void> {
        return IntAnnulerÉvénement(_source).annulerÉvénement(id)
    }

    suspend fun demanderLesParticipations(idUtilisateur: Int): List<Événement> {
        val participations =
            IntGetÉvènementParParticipant(_source).demanderMesParticipations(idUtilisateur)
        return participations
    }

    suspend fun demanderSesPropreÉvènement(idUtilisateur: Int): List<Événement> {
        val mesÉvènements =
            IntGetÉvènementsParOrganisateur(_source).demanderMesÉvènements(idUtilisateur)
        return mesÉvènements
    }

    suspend fun allerChercherInfoÉvenement(id : Int) : Événement? {
        val evenement = IntDétailÉvenement(_source).getInfoÉvenement(id)
        return evenement
    }

    suspend fun ajouterParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return IntDétailÉvenement(_source).ajouterParticipation(utilisateurÉvénement)
    }

    suspend fun retirerParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return IntDétailÉvenement(_source).retirerParticipation(utilisateurÉvénement)
    }

    suspend fun getCommentairesDansÉvénement(id : Int) : List<Commentaire> {
        return IntGetCommentaires(_source).getCommentairesParÉvénement(id)
    }

    suspend fun créerCommentaire(commentaire: Commentaire) : Response<Void>
    {
        return IntCreerCommentaire(_source = _source).CreerCommentaire(commentaire = commentaire)
    }

    fun getImageÉvénement(id: Int): String {
        return _source.getImageEvenement(id)
    }


}