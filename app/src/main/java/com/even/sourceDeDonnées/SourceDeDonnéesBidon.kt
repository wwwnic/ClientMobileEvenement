package com.even.sourceDeDonnées

import com.even.R
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SourceDeDonnéesBidon : ISourceDeDonnées {

    val listeUtils: ArrayList<Utilisateur> = ArrayList<Utilisateur>()
    val listeEvens: ArrayList<Événement> = ArrayList<Événement>()
    val listeUtilEven: ArrayList<UtilisateurÉvénement> = ArrayList<UtilisateurÉvénement>()

    init {
        val util = Utilisateur(
            1,
            "Bob",
            "123",
            "bob@gmail.com",
            "(514)123-4567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        val util2 = Utilisateur(
            2,
            "Dude",
            "456",
            "dude@gmail.com",
            "(514)999-9999",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        val util3 = Utilisateur(
            3,
            "Patrick",
            "789",
            "patrick@crosemont.qc.ca",
            "(514)111-1111",
            SimpleDateFormat("yyyy.MM").format(Date())
        )
        listeUtils.add(util)
        listeUtils.add(util2)
        listeUtils.add(util3)
        val évén = Événement(
            1,
            "Party chez Bob",
            "Maison de Bob",
            "2021-11-24T18:00",
            1,
            "gros party chez Bob let's gooooooo!"
        )

        val évén2 = Événement(
            2,
            "Autre Party chez Bob",
            "bbbbbb",
            "2021-11-24T18:00",
            1,
            "hey salut"
        )

        val évén3 = Événement(
            3,
            "Réunion des bricoleurs",
            "Bricoville",
            "2021-11-24T18:00",
            2,
            "ayoyeeeeeeeee"
        )
        listeEvens.add(évén)
        listeEvens.add(évén2)
        listeEvens.add(évén3)
        val utilEven = UtilisateurÉvénement(
            2,
            1
        )

        val utilEven2 = UtilisateurÉvénement(
            3,
            1
        )

        val utilEven3 = UtilisateurÉvénement(
            1,
            3
        )

        val utilEven4 = UtilisateurÉvénement(
            3,
            3
        )
        listeUtilEven.add(utilEven)
        listeUtilEven.add(utilEven2)
        listeUtilEven.add(utilEven3)
        listeUtilEven.add(utilEven4)
    }

    override suspend fun getAllEvenements(): List<Événement> {
        resetEvenements()
        return listeEvens
    }

    override suspend fun getEvenementParId(id: Int): Événement? {
        return listeEvens.filter { u -> u.idEvenement == id }[0]
    }

    override suspend fun getAllUtilisateurs(): List<Utilisateur> {
        return listeUtils
    }

    override suspend fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        return listeUtilEven
    }

    override suspend fun creerUtilisateur(utilisateur: Utilisateur): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun creerEvenement(evenement: Événement): Événement? {
        listeEvens.add(evenement)
        return evenement
    }

    override suspend fun modifierEvenement(evenement: Événement): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun supprimerEvenement(id: Int): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun getUtilisateurParId(id: Int): Utilisateur? {
        return listeUtils.filter { u -> u.idUtilisateur == id }[0]
    }

    override suspend fun getUtilisateursParNom(nom: String): List<Utilisateur> {
        return listeUtils.filter { u -> u.nomUtilisateur == nom }
    }

    override suspend fun getEvenementParParticipation(id: Int): List<Événement> {
        TODO("Not yet implemented")
    }

    override suspend fun getEvenementsParOrganisateur(id: Int): List<Événement> {
        TODO("Not yet implemented")
    }

    override suspend fun demanderProfil(
        identifiantUtilisateur: Utilisateur
    ): Utilisateur? {
        return listeUtils.filter { u -> u.nomUtilisateur == identifiantUtilisateur.nomUtilisateur }[0]
    }

    override suspend fun getEvenementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        resetEvenements()
        return listeEvens.filter { e -> e.nomEvenement.contains(nom) }
    }

    override fun getImageEvenement(id: Int): String {
        return R.drawable.imageevenementbidon.toString()
    }

    override fun getImageUtilisateur(id: Int): String {
        return R.drawable.imageutilisateurbidon.toString()
    }

    private fun resetEvenements() {
        for(e : Événement in listeEvens) {
            e.date = "2021-11-24T18:00"
        }
    }
}