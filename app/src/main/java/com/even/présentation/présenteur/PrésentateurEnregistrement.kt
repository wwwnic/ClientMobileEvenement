package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import kotlinx.coroutines.*

/**
 * Permet de faire les traitements dans la vue d'enregistrement
 *
 * @property vue Le vue VueEnregistrement
 * @property modeleAuthentification Le modèle d'authentification
 * @property validateur Le validateur d'entrée utilisateur
 */
class PrésentateurEnregistrement(
    val vue: IEnregistrement.IVue,
    val modeleAuthentification: ModèleAuthentification,
    val validateur: ValidateurTextuel,
    val dispatcher: UnCoroutineDispatcher
) : IEnregistrement.IPrésentateur {
    private var coroutileEnregistrement: Job? = null


    /**
     * Vérifie les informations entrées, si elles sont valides, envoie une requête contenant les informations au modèle
     *
     * @param nomUtilisateur Un nom qu'un utilisateur a entré
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     * @param courriel Un courriel qu'un utilisateur a entré
     * @param telephone Un telephone qu'un utilisateur a entré
     */
    override fun traiterRequêteEnregistrerUtilisateur(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ) {
        val entréesValide = validerTousLesEntrées(nomUsager, motDePasse, courriel, telephone)
        if (entréesValide) {
            lancerRequeteEnregistrement(nomUsager, motDePasse, courriel, telephone)
        } else {
            vue.afficherToastErreurEnregistrement()
        }
    }

    /**
     * Enregistre l'utilisateur via le model
     *
     * @param nomUtilisateur un nom utilisateur qui a été validé
     * @param motDePasse Un mot de passe qui a été validé
     * @param courriel Un courriel qui a été validé
     * @param telephone Un telephone qui a été validé
     */
    private fun lancerRequeteEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ) {
        coroutileEnregistrement = CoroutineScope(dispatcher.io).launch {
            try {
                var reponseApi = modeleAuthentification.effectuerEnregistrement(
                    nomUsager.toString(),
                    motDePasse.toString(),
                    courriel.toString(),
                    telephone.toString()
                )
                if (reponseApi.isSuccessful) {
                    withContext(dispatcher.main) {
                        vue.naviguerVersConnexion()
                        vue.afficherToastSuccesEnregistrement()
                    }
                } else {
                    vue.afficherToastErreurEnregistrement()
                    Log.e(
                        "Évèn",
                        "Le serveur a retourné une erreur"
                    )
                }
            } catch (e: Exception) {
                vue.afficherToastErreurServeur()
                Log.e("Évèn", "Erreur d'accès à l'API", e)
            }
        }
    }

    /**
     * Valide le nom et le mot de passe qu'un utilisateur a rentré.
     *
     * @param nomUsager Un nom qu'un utilisateur a entré
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     * @param courriel Un courriel qu'un utilisateur a entré
     * @param telephone Un telephone qu'un utilisateur a entré
     * @return Les entrées de l'utilisateur sont valides
     */
    private fun validerTousLesEntrées(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ): Boolean {
        val estNomUsagerValide = this.traiterRequêteValiderNomUsager(nomUsager)
        val estMotDePasseValide = this.traiterRequêteValiderMotDePasse(motDePasse)
        val estCourrielValide = this.traiterRequêteValiderCourriel(courriel)
        val estTelephoneValide = this.traiterRequêteValiderTelephone(telephone)
        val entréesValide =
            estCourrielValide && estTelephoneValide && estNomUsagerValide && estMotDePasseValide
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
        vue.afficherErreurNomUsager(!estValide)
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

    /**
     * Valide le courriel qu'un utilisateur a rentré
     *
     * @param courriel Un courriel qu'un utilisateur a entré
     * @return Le courriel entrée est valide
     */
    private fun traiterRequêteValiderCourriel(courriel: CharSequence): Boolean {
        val estValide = validateur.validerCourriel(courriel)
        vue.afficherErreurCourriel(!estValide)
        return estValide
    }

    /**
     * Valide le telephone qu'un utilisateur a rentré
     *
     * @param telephone Un telephone qu'un utilisateur a entré
     * @return Le telephone entrée est valide
     */
    private fun traiterRequêteValiderTelephone(telephone: CharSequence): Boolean {
        val estValide = validateur.validerTelephone(telephone)
        vue.afficherErreurTéléphone(!estValide)
        return estValide
    }
}
