package com.even.sourceDeDonnées

import com.even.R
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SourceDeDonnéesBidon : ISourceDeDonnées {

    val listeUtils: ArrayList<Utilisateur> = ArrayList<Utilisateur>()
    val listeEvens: ArrayList<Événement> = ArrayList<Événement>()
    val listeUtilEven: ArrayList<UtilisateurÉvénement> = ArrayList<UtilisateurÉvénement>()
    val listeComm : ArrayList<Commentaire> = ArrayList<Commentaire>()

    init {
        val util = Utilisateur(
            1,
            "BobB",
            "pw123",
            "bob@gmail.com",
            "(514)123-4567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        val util2 = Utilisateur(
            2,
            "Dude",
            "4567",
            "dude@gmail.com",
            "(514)999-9999",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        val util3 = Utilisateur(
            3,
            "Patrick",
            "7891",
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
        val comm1 = Commentaire(
            1,
            1,
            1,
            "2021-12-01T17:00",
            "hey salut"
        )
        val comm2 = Commentaire(
            2,
            1,
            2,
            "2021-12-01T18:00",
            "bonjour"
        )
        listeComm.add(comm1)
        listeComm.add(comm2)
    }

    override suspend fun getAllEvenements(): List<Événement> {
        resetEvenements()
        return listeEvens
    }

    override suspend fun getÉvénementParId(id: Int): Événement? {
        resetEvenements()
        return listeEvens.filter { u -> u.idEvenement == id }[0]
    }

    override suspend fun getAllUtilisateurs(): List<Utilisateur> {
        return listeUtils
    }

    suspend fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        return listeUtilEven
    }

    override suspend fun getUtilisateursDansEvenement(idEvenement: Int): List<Utilisateur> {
        TODO("Not yet implemented")
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

    override suspend fun getÉvénementsParParticipation(id: Int): List<Événement> {
        var listeParticipations = listeUtilEven.filter { ue -> ue.idUtilisateur == id}
        var listeEvenements : MutableList<Événement> = mutableListOf()
        listeEvens.forEach {
            e -> listeParticipations.forEach {
                ue -> if (ue.idEvenement == e.idEvenement)
                listeEvenements.add(e)
            }
        }
        return listeEvenements
    }

    override suspend fun getÉvénementsParOrganisateur(id: Int): List<Événement> {
        return listeEvens.filter { e -> e.idOrganisateur == id }
    }

    override suspend fun demanderProfil(
        identifiantUtilisateur: Utilisateur
    ): Utilisateur? {
        return listeUtils.filter { u -> u.nomUtilisateur == identifiantUtilisateur.nomUtilisateur }[0]
    }

    override suspend fun getÉvénementsParRecherche(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ): List<Événement> {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentairesParEvenement(id: Int): List<Commentaire> {
        TODO("Not yet implemented")
    }

    override suspend fun creerCommentaire(commentaire: Commentaire): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun ajouterParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun retirerParticipation(utilisateurÉvenement: UtilisateurÉvénement): Response<Void> {
        TODO("Not yet implemented")
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

    private fun resetCommentaires() {
        for(c : Commentaire in listeComm) {
            c.dateCommentaire = "2021-12-01T18:00"
        }
    }
}