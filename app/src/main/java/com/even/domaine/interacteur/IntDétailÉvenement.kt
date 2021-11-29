package com.even.domaine.interacteur

import android.util.Log
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ApiClient.apiService
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class IntDétailÉvenement(val api : ISourceDeDonnées) {

    suspend fun getInfoÉvenement(id : Int) : Événement? {
        val reponseRequête = api.getÉvenementParId(id)
        return reponseRequête
    }

    suspend fun ajouterParticipation(utilisateurÉvenement : UtilisateurÉvénement) : Response<Void>{
        val reponseRequête = apiService.ajouterParticipation(utilisateurÉvenement)
        return reponseRequête
    }

    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement) : Response<Void> {
        val reponseRequête = apiService.retirerParticipation(utilisateurÉvenement)
        return reponseRequête
    }

}