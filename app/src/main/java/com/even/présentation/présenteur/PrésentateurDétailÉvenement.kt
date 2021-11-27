package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleDétailÉvenement
import kotlinx.coroutines.*

class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
) : IDétailÉvenement.IPrésentateur {
    private val handlerRéponse: Handler

    private var coroutileDétailÉvenement: Job? = null

    private val MSG_RÉUSSI = 0
    private val MSG_ECHEC = 1
    private val MSG_ANNULER = 2

    init {
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG_RÉUSSI) {


                } else if (msg.what == MSG_ECHEC) {
                    vue.afficherToastErreurServeur()
                    Log.e(
                        "Évèn",
                        "Le serveur a retourné une erreur"
                    )
                } else {
                    coroutileDétailÉvenement?.cancel()
                    vue.afficherToastErreurServeur()
                    Log.e("Évèn", "Erreur d'accès à l'API", msg.obj as Throwable)
                }
            }
        }
    }

    override fun traiterRequêteAfficherDétailÉvenement(idEvenement: Int) {
        var evenement: Événement? = null
        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {
            var msg: Message? = null
            try {
                evenement = ModèleDétailÉvenement().allerChercherInfoÉvenement(idEvenement)
                withContext(Dispatchers.Main) {
                    msg = handlerRéponse.obtainMessage(MSG_RÉUSSI)
                    msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                    vue.setInfo(evenement!!)
                }
            } catch (e: Exception) {
                msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
            }
            handlerRéponse.sendMessage(msg!!)
        }
    }

}