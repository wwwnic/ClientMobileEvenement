package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Response

class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
) : IDétailÉvenement.IPrésentateur {
    private val handlerRéponse: Handler

    private var evenementEnCours: Événement? = null

    private var coroutileDétailÉvenement: Job? = null

    private var participation : Boolean? = null
    var listeÉvénementsClient : List<Événement>? = null
    val idUtilisateurConnecté = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!

    private val MSG_ECHEC = 0
    private val MSG_ANNULER = 1
    private val MSG_RÉUSSI_GET_INFO = 2

    init {
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                if (msg.what == MSG_RÉUSSI_GET_INFO) {
                    vue.setInfo(evenementEnCours!!)

                    if (participation == true) {
                        vue.afficherNePlusParticiper()
                    } else {
                        vue.afficherParticipation()
                    }

                } else if (msg.what == MSG_RÉUSSI_AJOUTER_PARTICIPATION) {
                    participation = true
                    vue.afficherNePlusParticiper()
                    vue.afficherToastParticipationAjouté()

                } else if  (msg.what == MSG_RÉUSSI_RETIRER_PARTICIPATION) {
                    participation = false
                    vue.afficherParticipation()
                    vue.afficherToastParticipationRetiré()

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
        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {
            var msg: Message?
            try {
                evenementEnCours = ModèleÉvénements().allerChercherInfoÉvenement(idEvenement)
                listeÉvénementsClient = ModèleÉvénements().demanderLesParticipations(idUtilisateurConnecté)

                withContext(Dispatchers.Main) {
                    vérifierParticipation()
                    if (evenementEnCours != null && participation != null) {
                        msg = handlerRéponse.obtainMessage(MSG_RÉUSSI_GET_INFO)
                    } else {
                        msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                    }
                }
            } catch (e: Exception) {
                msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
            }
            handlerRéponse.sendMessage(msg!!)
        }
    }

    override fun traiterRequêteAjouterParticipation(idEvenement: Int) {
        var reponseApi : Response<Void>
        val utilisateurÉvenement = UtilisateurÉvénement(idUtilisateurConnecté, idEvenement)

        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {
            var msg : Message?

            if (participation == false) {
                try {
                    reponseApi = ModèleÉvénements().ajouterParticipation(utilisateurÉvenement)

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            msg = handlerRéponse.obtainMessage(MSG_RÉUSSI_AJOUTER_PARTICIPATION)
                        } else {
                            msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                        }
                    }
                } catch (e : Exception){
                    msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
                }
            } else {
                try {
                    reponseApi = ModèleÉvénements().retirerParticipation(utilisateurÉvenement)

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            msg = handlerRéponse.obtainMessage(MSG_RÉUSSI_RETIRER_PARTICIPATION)
                        } else {
                            msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                        }
                    }
                } catch (e : Exception){
                    msg = handlerRéponse.obtainMessage(MSG_ANNULER, e)
                }
            }
            handlerRéponse.sendMessage(msg!!)
        }
    }

    private fun vérifierParticipation() {
        if (!(listeÉvénementsClient?.isEmpty())!!) {
            for (evenement : Événement in listeÉvénementsClient!!) {
                if (evenement.idEvenement == evenementEnCours!!.idEvenement) {
                    participation = true
                    break
                } else {
                    participation = false
                }
            }
        } else {
            participation = false
        }
    }

}