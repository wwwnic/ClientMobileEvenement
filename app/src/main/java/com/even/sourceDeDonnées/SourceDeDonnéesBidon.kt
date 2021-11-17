package com.even.sourceDeDonnées

import com.even.R
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import java.util.*

class SourceDeDonnéesBidon : ISourceDeDonnées {
    override fun getAllUtilisateurs(): List<Utilisateur> {
        var listeUtils : ArrayList<Utilisateur> = ArrayList<Utilisateur>()
        var util : Utilisateur = Utilisateur(
            1,
            "Bob",
            "123",
            "bob@gmail.com",
            "(514)123-4567",
            Calendar.getInstance().time
        )

        var util2 : Utilisateur = Utilisateur(
            2,
            "Dude",
            "456",
            "dude@gmail.com",
            "(514)999-9999",
            Calendar.getInstance().time
        )

        var util3 : Utilisateur = Utilisateur(
            3,
            "Patrick",
            "789",
            "patrick@crosemont.qc.ca",
            "(514)111-1111",
            Calendar.getInstance().time
        )
        listeUtils.add(util)
        listeUtils.add(util2)
        listeUtils.add(util3)
        return listeUtils
    }

    override fun getAllEvenements(): List<Événement> {
            var listeEvens : ArrayList<Événement> = ArrayList<Événement>()
            var évén : Événement = Événement(
                1,
                "Party chez Bob",
                "Maison de Bob",
                Calendar.getInstance().time,
                1, R.drawable.wowimg,
                "gros party chez Bob let's gooooooo!"
            )

            var évén2 : Événement = Événement(
                2,
                "Autre Party chez Bob",
                "bbbbbb",
                Calendar.getInstance().time,
                1, R.drawable.wowimg,
                "hey salut"
            )

            var évén3 : Événement = Événement(
                3,
                "Réunion des bricoleurs",
                "Bricoville",
                Calendar.getInstance().time,
                2, R.drawable.wowimg,
                "ayoyeeeeeeeee"
            )
            listeEvens.add(évén)
            listeEvens.add(évén2)
            listeEvens.add(évén3)
            return listeEvens
    }

    override fun getUtilisateursEvenement(): List<UtilisateurÉvénement> {
        var listeUtilEven : ArrayList<UtilisateurÉvénement> = ArrayList<UtilisateurÉvénement>()

        var utilEven : UtilisateurÉvénement = UtilisateurÉvénement(
            2,
            1
        )

        var utilEven2 : UtilisateurÉvénement = UtilisateurÉvénement(
            3,
            1
        )

        var utilEven3 : UtilisateurÉvénement = UtilisateurÉvénement(
            1,
            3
        )

        var utilEven4 : UtilisateurÉvénement = UtilisateurÉvénement(
            3,
            3
        )
        listeUtilEven.add(utilEven)
        listeUtilEven.add(utilEven2)
        listeUtilEven.add(utilEven3)
        listeUtilEven.add(utilEven4)
        return listeUtilEven
    }
}