package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ApiClient.apiService
import retrofit2.Response

class SourceDeDonnéesAPI(val serviceApi: IApiService) : ISourceDeDonnées {
    constructor() : this(apiService)

    override suspend fun getAllUtilisateurs(): List<Utilisateur> {
        var liste: List<Utilisateur> = ArrayList<Utilisateur>()

        var reponseApi = apiService.getAllUtilisateurs()
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Utilisateur>
        }
        return liste
    }

    override suspend fun getAllEvenements(): List<Événement> {
        var liste: List<Événement> = ArrayList<Événement>()

        var reponseApi = apiService.getAllEvenements()
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Événement>
        }
        return liste
    }

    override suspend fun getEvenementParId(id: Int): Événement? {
        var reponseApi = apiService.getEvenementParId(id)
        var événement: Événement? = null
        if (reponseApi.isSuccessful) {
            événement = reponseApi.body() as Événement
        }
        return événement
    }


    override suspend fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        TODO("Not yet implemented")
    }

    override suspend fun creerUtilisateur(utilisateur: Utilisateur): Response<Void> {
        return apiService.creerUtilisateur(utilisateur)
    }

    override suspend fun demanderProfil(identifiantUtilisateur: Utilisateur): Utilisateur? {
        return serviceApi.demanderProfil(identifiantUtilisateur).body()
    }

    override suspend fun getEvenementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        var liste: List<Événement> = ArrayList<Événement>()

        var reponseApi = apiService.getEvenementsParRecherche(nom, mois, location, organisateur)
        if (reponseApi.isSuccessful) {
            liste = reponseApi.body() as List<Événement>
        }
        return liste
    }

    override suspend fun creerEvenement(evenement: Événement): Événement? {
        var reponseApi = apiService.creerEvenement(evenement = evenement)
        var newEvenement: Événement? = null
        if (reponseApi.isSuccessful) {
            newEvenement = reponseApi.body() as Événement
        }
        return newEvenement
    }

    override suspend fun modifierEvenement(evenement: Événement): Response<Void> {
        return apiService.updateEvenement(evenement = evenement)
    }

    override suspend fun supprimerEvenement(id: Int): Response<Void> {
        return apiService.deleteEvenement(id)
    }

    override suspend fun getUtilisateurParId(id: Int): Utilisateur? {
        var reponseApi = apiService.getUtilisateurParId(id)
        var utilisateur: Utilisateur? = null
        if (reponseApi.isSuccessful) {
            utilisateur = reponseApi.body() as Utilisateur
        }
        return utilisateur
    }

    override suspend fun getUtilisateursParNom(nom: String): List<Utilisateur> {
        var reponseApi = apiService.getUtilisateursParNom(nom)
        var utilisateurs: List<Utilisateur> = ArrayList<Utilisateur>()
        if (reponseApi.isSuccessful) {
            utilisateurs = reponseApi.body() as List<Utilisateur>
        }
        return utilisateurs
    }

    override suspend fun getEvenementParParticipation(id: Int): List<Événement> {
        val reponseApi = apiService.getEvenementsParParticipation(id)
        return if (reponseApi.isSuccessful) reponseApi.body()!! else listOf()
    }

    override suspend fun getEvenementsParOrganisateur(id: Int): List<Événement> {
        val reponseApi = apiService.getEvenementsParOrganisateur(id)
        return if (reponseApi.isSuccessful) reponseApi.body()!! else listOf()
    }

    override fun getImageUtilisateur(id: Int): String {
        return "http://10.0.0.149:23784/images/utilisateurs/${id}.jpg"
    }

    override fun getImageEvenement(id: Int): String {
        return "http://10.0.0.149:23784/images/evenements/${id}.jpg"
    }
}