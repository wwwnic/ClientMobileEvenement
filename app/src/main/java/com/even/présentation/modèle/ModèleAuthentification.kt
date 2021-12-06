package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.domaine.interacteur.IntEnregistrement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

@testOuvert
class ModèleAuthentification() {

    companion object {
        var utilisateurConnecté: Utilisateur? = null
        lateinit var _source: ISourceDeDonnées
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
    }

    fun ajouterUnUtilisateur(utilisateur: Utilisateur?){
        utilisateurConnecté = utilisateur
    }

    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Utilisateur? {
        val identifiantUtilisateur = Utilisateur(nomUtilisateur.toString(), motDePasse.toString())
        val utilisateurRecuperé =
            IntConnexion(_source).connexionDemanderProfil(identifiantUtilisateur)
        utilisateurConnecté = utilisateurRecuperé
        return utilisateurRecuperé
    }

    suspend fun effectuerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ): Response<Void> {
        val reponseBodyRequête = IntEnregistrement(_source).enregisterNouvelUtilisateur(
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