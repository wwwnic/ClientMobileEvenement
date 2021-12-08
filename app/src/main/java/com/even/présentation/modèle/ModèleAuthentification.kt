package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.domaine.interacteur.IntEnregistrement
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class ModèleAuthentification(
    val intConnexion: IntConnexion,
    val intEregistrement: IntEnregistrement
) {

    companion object {
        var utilisateurConnecté: Utilisateur? = null
    }

    fun ajouterUnUtilisateur(utilisateur: Utilisateur?) {
        utilisateurConnecté = utilisateur
    }

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Utilisateur? {
        val identifiantUtilisateur = Utilisateur(nomUtilisateur.toString(), motDePasse.toString())
        val utilisateurRecuperé =
            intConnexion.connexionDemanderProfil(identifiantUtilisateur)
        return utilisateurRecuperé
    }

    suspend fun effectuerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ): Response<Void> {
        val reponseBodyRequête = intEregistrement.enregisterNouvelUtilisateur(
            Utilisateur(
                nomUsager.toString(),
                motDePasse.toString(),
                courriel.toString(),
                telephone.toString()
            )
        )
        return reponseBodyRequête
    }
}