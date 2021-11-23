package com.even.domaine.interacteur

import android.util.Log
import com.even.sourceDeDonnées.ApiClient.apiService

class IntDétailÉvenement {

    suspend fun getInfoÉvenement(id : Int) {
        var reponseRequête = apiService.getÉvenementParId(id)
        Log.i("Réponse GET", reponseRequête.toString())
    }

}