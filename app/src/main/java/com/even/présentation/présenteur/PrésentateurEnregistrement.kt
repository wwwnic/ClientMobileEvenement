package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

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
    val validateur: ValidateurTextuel
) : IEnregistrement.IPrésentateur {
    private val handlerRéponse: Handler

    private var coroutileEnregistrement: Job? = null

    private val MSG_RÉUSSI = 0
    private val MSG_ECHEC = 1
    private val MSG_ANNULER = 2

    init {
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG_RÉUSSI) {
                    vue.naviguerVersConnexion()
                    vue.afficherToastSuccesEnregistrement()

                } else if (msg.what == MSG_ECHEC) {
                    vue.afficherToastErreurServeur()
                    Log.e(
                        "Évèn",
                        "Le serveur a retourné une erreur"
                    )
                } else {
                    coroutileEnregistrement?.cancel()
                    vue.afficherToastErreurServeur()
                    Log.e("Évèn", "Erreur d'accès à l'API", msg.obj as Throwable)
                }
            }
        }
    }

    /**
     * Vérifie les informations entrées, si elles sont valides, envoie une requête contenant les informations au modèle
     *
     * @param nomUtilisateur Un nom qu'un utilisateur a entré
     * @param motDePasse Un mot de passe qu'un utilisateur a entré
     * @param courriel Un courriel qu'un utilisateur a entré
     * @param telephone Un telephone qu'un utilisateur a entré
     */
    override fun traiterRequêteReclamerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ) {
        coroutileEnregistrement = CoroutineScope(Dispatchers.IO).launch {
            var msg: Message? = null
            try {
                var reponseApi = modeleAuthentification.effectuerEnregistrement(
                    nomUsager.toString(),
                    motDePasse.toString(),
                    courriel.toString(),
                    telephone.toString()
                )
                if (reponseApi.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        msg = handlerRéponse.obtainMessage(MSG_RÉUSSI)
                    }
                } else {
                    msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                }
            } catch (e: SocketTimeoutException) {
                msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
            } catch (e: InterruptedException) {
                msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
            }
            handlerRéponse.sendMessage(msg!!)
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
    override fun traiterRequêteValiderTousLesEntrées(
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
        if (!entréesValide) vue.afficherToastErreurEnregistrement()
        return (entréesValide)
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
