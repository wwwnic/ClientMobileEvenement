package com.even.présentation.modèle

import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.*
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class ModèleÉvénements {

    companion object {
        lateinit var _source: ISourceDeDonnées
        var newÉvénement: Événement? = null
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }

        suspend fun setNouvelÉvénement(événement: Événement) {
            événement.organisateur = IntGetUtilisateur(_source).getParId(événement.idOrganisateur)
            newÉvénement = événement
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

    suspend fun ajouterParticipation(utilisateurÉvénement: UtilisateurÉvénement): Response<Void> {
        return IntDétailÉvenement(_source).ajouterParticipation(utilisateurÉvénement)
    }

    suspend fun retirerParticipation(utilisateurÉvénement: UtilisateurÉvénement) : Response<Void> {
        return IntDétailÉvenement(_source).retirerParticipation(utilisateurÉvénement)
    }

    suspend fun allerChercherInfoÉvenement(id : Int) : Événement {
        val evenement = IntDétailÉvenement(_source).getInfoÉvenement(id)
        return evenement
    }


    fun getImageÉvénement(id: Int): String {
        return _source.getImageEvenement(id)
    }


}