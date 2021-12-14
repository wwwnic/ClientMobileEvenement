package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.domaine.interacteur.IntEnregistrement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testOuvert
import retrofit2.Response

/**
 * Permets de se connecter et de s'enregistrer dans l'application, retiens aussi une instance de l'utilisateur connecté.
 *
 * @property intConnexion un interacteur pour permettre la connexion
 * @property intEnregistrement un interacteur pour permettre de s'enregistrer
 */
@testOuvert
class ModèleAuthentification(
    val intConnexion: IntConnexion,
    val intEnregistrement: IntEnregistrement
) {

    constructor() : this(IntConnexion(_source), IntEnregistrement(_source))

    companion object {
        lateinit var _source: ISourceDeDonnées
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }

        var utilisateurConnecté: Utilisateur? = null
    }

    fun ajouterUnUtilisateur(utilisateur: Utilisateur?) {
        utilisateurConnecté = utilisateur
    }

    /**
     * Demande un profil avec les infromations de connexion
     *
     * @param nomUtilisateur Le nom de l'utilisateur à recuperer
     * @param motDePasse Le mot de passe de l'utilisateur à recuperer
     * @return Un utilisateur recuperé
     */
    suspend fun demanderProfilUtilisateur(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ): Utilisateur? {
        val identifiantUtilisateur = Utilisateur(nomUtilisateur.toString(), motDePasse.toString())
        val utilisateurRecuperé =
            intConnexion.connexionDemanderProfil(identifiantUtilisateur)
        return utilisateurRecuperé
    }


    /**
     * Permets d'enregistrer un profil
     *
     * @param nomUsager Le nom a enregistrer
     * @param motDePasse Le mot de passe a enregistrer
     * @param courriel Le courriel a enregistrer
     * @param telephone Le telephone a enregistrer
     * @return Une Reponse vide
     */
    suspend fun effectuerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ): Response<Void> {
        val reponseBodyRequête = intEnregistrement.enregisterNouvelUtilisateur(
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