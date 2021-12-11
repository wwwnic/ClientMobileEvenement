package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert

@testOuvert
class IntGetÉvénement(var _source: ISourceDeDonnées) {

    suspend fun getÉvénementParId(id: Int): Événement? {
        return _source.getÉvénementParId(id)
    }

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

    suspend fun getÉvénementsRécents(): List<Événement> {
        var listeEvenement = _source.getAllEvenements()
        if (listeEvenement.isNullOrEmpty()) listeEvenement = ArrayList<Événement>()
        listeEvenement.forEach { evenement ->
            evenement.organisateur = IntGetUtilisateur(_source).getParId(evenement.idOrganisateur)
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }.substring(0, 16)
        }
        return listeEvenement
    }

    fun getImageÉvénement(id: Int): String {
        return _source.getImageEvenement(id)
    }
}