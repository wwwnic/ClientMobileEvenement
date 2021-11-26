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
            Calendar.getInstance().time.toString(),
            1,
            "gros party chez Bob let's gooooooo!"
        )

        val évén2 = Événement(
            2,
            "Autre Party chez Bob",
            "bbbbbb",
            Calendar.getInstance().time.toString(),
            1,
            "hey salut"
        )

        val évén3 = Événement(
            3,
            "Réunion des bricoleurs",
            "Bricoville",
            Calendar.getInstance().time.toString(),
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
        return listeEvens
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
        TODO("Not yet implemented")
    }

    override suspend fun getUtilisateurParId(id: Int): Utilisateur? {
        TODO("Not yet implemented")
    }

    override suspend fun getEvenementParParticipation(id: Int): List<Événement> {
        TODO("Not yet implemented")
    }

    override suspend fun demanderProfil(
        identifiantUtilisateur: Utilisateur
    ): Utilisateur? {
        TODO("Not yet implemented")
    }

    override suspend fun getEvenementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        TODO("Not yet implemented")
    }

    override fun getImageEvenement(id: Int): String {
        return R.drawable.imageevenementbidon.toString()
    }

    override fun getImageUtilisateur(id: Int): String {
        return R.drawable.imageutilisateurbidon.toString()
    }
}