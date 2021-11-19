package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ApiClient.apiService
import kotlinx.coroutines.*

class SourceDeDonnéesAPI : ISourceDeDonnées {
    override fun getAllUtilisateurs(): List<Utilisateur> {
        var liste : List<Utilisateur> = ArrayList<Utilisateur>()

        CoroutineScope(Dispatchers.IO).launch {
            var reponseApi = apiService.getAllUtilisateurs()
            if (reponseApi.isSuccessful) {
                liste = reponseApi.body() as List<Utilisateur>
            }
        }
        return liste
    }

    override fun getAllEvenements(): List<Événement> {
        var liste : List<Événement> = ArrayList<Événement>()

        runBlocking {
            var reponseApi = apiService.getAllEvenements()
            if (reponseApi.isSuccessful) {
                liste = reponseApi.body() as List<Événement>
            }
        }
        return liste
    }

    override fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        TODO("Not yet implemented")
    }

    override fun creerUtilisateur() {
        TODO("Not yet implemented")
    }
}