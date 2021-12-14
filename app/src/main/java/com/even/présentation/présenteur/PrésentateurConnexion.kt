package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import kotlinx.coroutines.*

/**
 * Permet de faire les traitements dans la vue connexion ainsi que le modèle authentification
 *
 * @property vue la vue
 * @property modele le modele
 * @property validateur un valideur d'entrée utilisateur
 * @property dispatcher le context pour les coroutines
 */
class PrésentateurConnexion(
    val vue: IConnexion.IVue,
    val modele: ModèleAuthentification,
    val validateur: ValidateurTextuel,
    val dispatcher: UnCoroutineDispatcher
) : IConnexion.IPrésentateur {
    private var coroutileLogin: Job? = null

    /**
     * Vérifie les informations entrées, si elles sont valides, envoie une requête contenant les informations au modèle
     *
     * @param nomUtilisateur Un nom qu'un utilisateur a entré
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     */
    override fun traiterRequêteDemanderProfilPourConnexion(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ) {
        val entréesValide = validerLesEntréesConnexion(nomUtilisateur, motDePasse)
        if (entréesValide) {
            lancerRequeteConnexionApi(nomUtilisateur, motDePasse)
        } else {
            vue.afficherToastErreurConnexion()
        }
    }

    /**
     * Lance un coroutine qui permet de recuperer un utilisateur
     *
     * @param nomUtilisateur Un nom vérifié qu'un utilisateur a entré
     * @param motDePasseUn Un mot de passe vérifié qu'un utilisateur a entré
     */
    private fun lancerRequeteConnexionApi(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ) {
        coroutileLogin = CoroutineScope(dispatcher.io).launch {
            try {
                val utilisateurRecuperé =
                    modele.demanderProfilUtilisateur(nomUtilisateur, motDePasse)
                modele.ajouterUnUtilisateur(utilisateurRecuperé)
                val estUtilisateurExistant = utilisateurRecuperé?.nomUtilisateur != null
                withContext(dispatcher.main) {
                    if (estUtilisateurExistant) {
                        vue.naviguerVersFragmentPrincipal()
                        vue.afficherToastSuccesConnexion()
                    } else {
                        vue.afficherToastErreurConnexion()
                        vue.afficherErreurNomUtilisateur(!estUtilisateurExistant)
                        vue.afficherErreurMotDePasse(!estUtilisateurExistant)
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(dispatcher.main) {
                    vue.afficherToastErreurServeur()
                }
            }
        }
    }

    /**
     * Valide le nom et le mot de passe qu'un utilisateur a rentré.
     *
     * @param nomUsager Un nom qu'un utilisateur a entré
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     * @return Les entrées de l'utilisateur sont valides
     */
    private fun validerLesEntréesConnexion(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
    ): Boolean {
        val estNomUsagerValide = this.traiterRequêteValiderNomUsager(nomUsager)
        val estMotDePasseValide = this.traiterRequêteValiderMotDePasse(motDePasse)
        val entréesValide = estNomUsagerValide && estMotDePasseValide
        return entréesValide
    }

    /**
     * Valide le nom qu'un utilisateur a rentré.
     *
     * @param nomUsager Un nom qu'un utilisateur a entré
     * @return Le nom entrée est valide
     */
    private fun traiterRequêteValiderNomUsager(nomUsager: CharSequence): Boolean {
        val estValide = validateur.validerNomUsager(nomUsager)
        vue.afficherErreurNomUtilisateur(!estValide)
        return estValide
    }

    /**
     * Valide le mot de passe qu'un utilisateur a rentré
     *
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     * @return Le mot de passe entrée est valide
     */
    private fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean {
        val estValide = validateur.validerMotDePasse(motDePasse)
        vue.afficherErreurMotDePasse(!estValide)
        return estValide
    }
}