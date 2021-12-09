package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Response
import java.net.SocketTimeoutException

class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
) : IDétailÉvenement.IPrésentateur {
    private val handlerRéponse: Handler

    private var evenementEnCours: Événement? = ModèleÉvénements.événementPrésenté

    private var coroutileDétailÉvenement: Job? = null

    private var participation : Boolean? = null

    var listeÉvénementsClient : List<Événement>? = null
    val idUtilisateurConnecté = ModèleAuthentification.utilisateurConnecté?.idUtilisateur!!
    var nombreParticipant = 0

    private val MSG_ECHEC = 0
    private val MSG_ANNULER = 1
    private val MSG_RÉUSSI_GET_INFO = 2
    private val MSG_RÉUSSI_AJOUTER_PARTICIPATION = 3
    private val MSG_RÉUSSI_RETIRER_PARTICIPATION = 4

    init {
        handlerRéponse = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                if (msg.what == MSG_RÉUSSI_GET_INFO) {
                    vue.setInfo(evenementEnCours!!)
                    vue.setNombreParticipant(nombreParticipant)

                    if (evenementEnCours!!.idOrganisateur == idUtilisateurConnecté) {
                        vue.cacherBoutonParticipation()

                    } else if (participation == true) {
                        vue.afficherNePlusParticiper()

                    } else {
                        vue.afficherParticipation()
                    }

                } else if (msg.what == MSG_RÉUSSI_AJOUTER_PARTICIPATION) {
                    participation = true
                    vue.afficherNePlusParticiper()
                    vue.afficherToastParticipationAjouté()
                    vue.afficherApplicationCalendrierPourAjouter(setDatePourCalendrier())

                } else if  (msg.what == MSG_RÉUSSI_RETIRER_PARTICIPATION) {
                    participation = false
                    vue.afficherParticipation()
                    vue.afficherToastParticipationRetiré()
                    vue.afficherApplicationCalendrierPourEffacer(setDatePourCalendrier())

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
                evenementEnCours!!.urlImage = ModèleÉvénements().getImageÉvénement(evenementEnCours!!.idEvenement)

                listeÉvénementsClient = ModèleÉvénements().demanderLesParticipations(idUtilisateurConnecté)

                nombreParticipant = ModèleUtilisateurs().getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement).count()

                withContext(Dispatchers.Main) {
                    vérifierParticipation()
                    if (evenementEnCours != null && participation != null && nombreParticipant != 0) {
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

    override fun traiterRequêteAfficherParticipants() {
        var liste : List<Utilisateur> = ArrayList<Utilisateur>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = ModèleUtilisateurs().getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement)
                withContext(Dispatchers.Main) {
                    if (liste.isNotEmpty()) {
                        vue.afficherListeParticipants(liste,{ i -> ModèleUtilisateurs().getImageUtilisateur(i)})
                    } else {
                        vue.afficherToastErreurServeur()
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherToastErreurServeur()
                }
            }
        }
    }

    override fun traiterRequêteAfficherCommentaires() {
        var liste : List<Commentaire> = ArrayList<Commentaire>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val idEvenement = ModèleÉvénements.événementPrésenté!!.idEvenement
                liste = ModèleÉvénements().getCommentairesDansÉvénement(idEvenement)
                withContext(Dispatchers.Main) {
                    vue.afficherListeCommentaires(liste)
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherToastErreurServeur()
                }
            }
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

    private fun setDatePourCalendrier() : IntArray {
        val array = IntArray(5)
        val date = evenementEnCours!!.date
        array[0] = date.substring(0,4).toInt()
        array[1] = date.substring(5,7).toInt()
        array[2] = date.substring(8,10).toInt()
        array[3] = date.substring(11,13).toInt()
        array[4] = date.substring(14,16).toInt()
        return array
    }

}