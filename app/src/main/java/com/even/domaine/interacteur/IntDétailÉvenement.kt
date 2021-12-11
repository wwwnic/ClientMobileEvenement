package com.even.domaine.interacteur

import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class IntDétailÉvenement(val api: ISourceDeDonnées) {

    suspend fun ajouterParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = api.ajouterParticipation(utilisateurÉvenement)
        return reponseRequête
    }

    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        val reponseRequête = api.retirerParticipation(utilisateurÉvenement)
        return reponseRequête
    }

}