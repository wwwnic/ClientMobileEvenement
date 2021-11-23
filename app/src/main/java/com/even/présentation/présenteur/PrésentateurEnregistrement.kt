package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.ValidateurEntréesTextuel
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.modèle.ModèleEnregistrement
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class PrésentateurEnregistrement(
    val vue: IEnregistrement.IVue,
    val modèleEnregistrment: ModèleEnregistrement
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

    override fun traiterRequêteReclamerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ) {
        coroutileEnregistrement = CoroutineScope(Dispatchers.IO).launch {
            var msg: Message? = null
            try {
                var reponseApi = modèleEnregistrment.effectuerEnregistrement(
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

    override fun traiterRequêteValiderNomUsager(nomUsager: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerNomUsager(nomUsager)
        vue.afficherErreurNomUsager(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerMotDePasse(motDePasse)
        vue.afficherErreurMotDePasse(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderCourriel(courriel: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerCourriel(courriel)
        vue.afficherErreurCourriel(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderTelephone(telephone: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerTelephone(telephone)
        vue.afficherErreurTéléphone(!estValide)
        return estValide
    }
}