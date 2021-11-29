package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import retrofit2.Response

interface ISourceDeDonnées {

    suspend fun getAllUtilisateurs(): List<Utilisateur>
    suspend fun getAllEvenements(): List<Événement>
    suspend fun getÉvenementParId(id : Int): Événement?
    suspend fun getUtilisateursEvenement(): List<UtilisateurÉvénement>
    suspend fun creerUtilisateur(utilisateur: Utilisateur): Response<Void>
    suspend fun demanderProfil(utilisateur: Utilisateur): Utilisateur?
    suspend fun creerEvenement(evenement: Événement): Événement?
    suspend fun modifierEvenement(evenement: Événement) : Response<Void>
    suspend fun supprimerEvenement(id : Int) : Response<Void>
    suspend fun getUtilisateurParId(id: Int): Utilisateur?
    suspend fun getUtilisateursParNom(nom : String) : List<Utilisateur>
    suspend fun getEvenementParParticipation(id: Int): List<Événement>
    suspend fun getEvenementsParOrganisateur(id: Int): List<Événement>
    suspend fun getEvenementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement>
    suspend fun getEvenementParId(id : Int): Événement
    suspend fun ajouterParticipation(utilisateurÉvenement : UtilisateurÉvénement) : Response<Void>
    suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement) : Response<Void>
    fun getImageUtilisateur(id: Int): String
    fun getImageEvenement(id: Int): String
}