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

class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
) : IDétailÉvenement.IPrésentateur {
    private val handlerRéponse: Handler

    private var evenementEnCours: Événement? = null

    private var coroutileDétailÉvenement: Job? = null

    private var participant : Boolean? = null

    private val MSG_ECHEC = 0
    private val MSG_ANNULER = 1
    private val MSG_RÉUSSI_GET_INFO = 2
    private val MSG_RÉUSSI_AJOUTER_PARTICIPATION = 3
    private val MSG_RÉUSSI_RETIRER_PARTICIPATION = 4

    init {
        vérifierParticipation()
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == MSG_RÉUSSI_GET_INFO) {
                    vue.setInfo(evenementEnCours!!, participant!!)
                } else if (msg.what == MSG_RÉUSSI_AJOUTER_PARTICIPATION) {
                    vue.afficherToastParticipationAjouté()
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
                vérifierParticipation()
                withContext(Dispatchers.Main) {
                    if (evenementEnCours != null) {
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
        val idUtilisateurConnecté = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!

        val utilisateurÉvenement = UtilisateurÉvénement(idUtilisateurConnecté, idEvenement)

        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {
            var msg : Message? = null

            try {
                if (participant == false) {
                    var reponseApi = ModèleÉvénements().ajouterParticipation(utilisateurÉvenement)

                    if (reponseApi.isSuccessful) {
                        msg = handlerRéponse.obtainMessage(MSG_RÉUSSI_AJOUTER_PARTICIPATION)
                        vue.afficherNePlusParticiper()
                    } else {
                        msg = handlerRéponse.obtainMessage(MSG_ECHEC)
                    }
                } else {
                    var reponseApi = ModèleÉvénements().retirerParticipation(utilisateurÉvenement)

                    if (reponseApi.isSuccessful) {
                        msg = handlerRéponse.obtainMessage(MSG_RÉUSSI_RETIRER_PARTICIPATION)
                        vue.afficherParticipation()
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

    private fun vérifierParticipation() {
        val idUtilisateurConnecté = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!
        var listeÉvénements : List<Événement>?

        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {
            var msg : Message? = null
            try {
                listeÉvénements = ModèleÉvénements().demanderLesParticipations(idUtilisateurConnecté)

                if (listeÉvénements != null) {
                    withContext(Dispatchers.Main) {
                        for (evenement : Événement in listeÉvénements!!) {
                            if (evenement.idEvenement == evenementEnCours!!.idEvenement) {
                                participant = true
                                break
                            } else {
                                participant = false
                            }
                        }
                    }
                }
            } catch (e : Exception){
                msg = handlerRéponse.obtainMessage(MSG_ECHEC)
            }


        }
    }

}