package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.ValidateurEntréesTextuel
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.modèle.ModèleEnregistrement
import kotlinx.coroutines.*

class PrésentateurEnregistrement(
    var vue: IEnregistrement.IVue,
    var modèleEnregistrment: ModèleEnregistrement
) : IEnregistrement.IPrésentateur {

    private val handlerRéponse: Handler

    private var coroutileEnregistrement: Job? = null

    private val MSG_RÉUSSI = 0
    private val MSG_ERREUR = 1
    private val MSG_ANNULER = 2

    init {
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG_RÉUSSI) {
                    vue.naviguerVersConnexion()
                    vue.afficherToastSuccesEnregistrement()

                } else if (msg.what == MSG_ERREUR) {
                    vue.afficherToastErreurServeur()
                    Log.e(
                        "handler enregistrement",
                        "Erreur de connexion au serveur / réponse incompatible"
                    )
                } else {
                    coroutileEnregistrement?.cancel()
                    vue.afficherToastErreurServeur()
                    Log.e("handler enregistrement", "La requête a rencontré une erreur")
                }
            }
        }
    }

    override fun traiterRequêteReclamerEnregistrement(
        username: String,
        password: String,
        email: String,
        phone: String
    ) {
        coroutileEnregistrement = CoroutineScope(Dispatchers.IO).launch {
            var msg: Message? = null

            try {
                var reponseApi = modèleEnregistrment.effectuerEnregistrement(
                    username,
                    password,
                    email,
                    phone
                )
                if (reponseApi.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        msg = handlerRéponse.obtainMessage(MSG_RÉUSSI)
                    }
                } else {
                    msg = handlerRéponse.obtainMessage(MSG_ERREUR)
                }
            } catch (e: InterruptedException) {
                msg = handlerRéponse.obtainMessage(MSG_ANNULER)
            }
            handlerRéponse.sendMessage(msg!!)
        }
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