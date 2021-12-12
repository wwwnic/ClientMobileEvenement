package com.even.présentation.présenteur

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.even.domaine.entité.*
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testOuvert
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Response
import java.net.SocketTimeoutException

@testOuvert
class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
    var modeleAu : ModèleAuthentification,
    var modèleUtilisateurs : ModèleUtilisateurs,
    var modèleÉvénements : ModèleÉvénements,
    val dispatcher: UnCoroutineDispatcher

) : IDétailÉvenement.IPrésentateur {

    private var evenementEnCours: Événement? = ModèleÉvénements.événementPrésenté

    private var coroutileDétailÉvenement: Job? = null

    var participation : Boolean? = null

    var listeÉvénementsClient : List<Événement>? = null
    var idUtilisateurConnecté = ModèleAuthentification.utilisateurConnecté?.idUtilisateur!!
    var nombreParticipant = 0

    override fun traiterRequêteAfficherDétailÉvenement(idEvenement: Int) {
        coroutileDétailÉvenement = CoroutineScope(dispatcher.io).launch {
            try {
                evenementEnCours!!.urlImage = modèleÉvénements.getImageÉvénement(evenementEnCours!!.idEvenement)

                listeÉvénementsClient = modèleÉvénements.demanderLesParticipations(idUtilisateurConnecté)

                nombreParticipant = modèleUtilisateurs.getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement).count()

                withContext(Dispatchers.Main) {
                    vue.montrerLesDetailsEvenement()
                    vérifierParticipation()

                    if (evenementEnCours != null && participation != null && nombreParticipant != 0) {
                        vue.setInfo(evenementEnCours!!)
                        vue.setNombreParticipant(nombreParticipant)

                        if (evenementEnCours!!.idOrganisateur == idUtilisateurConnecté) {
                            vue.cacherBoutonParticipation()

                        } else if (participation == true) {
                            vue.afficherNePlusParticiper()

                        } else {
                            vue.afficherParticipation()
                        }

                    } else {
                        vue.afficherToastErreurServeur()
                        Log.e(
                            "Évèn",
                            "Le serveur a retourné une erreur"
                        )
                    }
                }
            } catch (e: Exception) {
                coroutileDétailÉvenement?.cancel()
                vue.afficherToastErreurServeur()
                Log.e("Évèn", "Erreur d'accès à l'API")
            }
        }
    }

    override fun traiterRequêteAjouterRetirerParticipation(idEvenement: Int) {
        var reponseApi : Response<Void>
        val utilisateurÉvenement = UtilisateurÉvénement(idUtilisateurConnecté, idEvenement)

        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {

            if (participation == false) {
                try {
                    reponseApi = modèleÉvénements.ajouterParticipation(utilisateurÉvenement)

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            participation = true
                            vue.afficherNePlusParticiper()
                            vue.afficherToastParticipationAjouté()
                            vue.afficherApplicationCalendrierPourAjouter(setDatePourCalendrier())
                        } else {
                            vue.afficherToastErreurServeur()
                            Log.e(
                                "Évèn",
                                "Le serveur a retourné une erreur"
                            )
                        }
                    }
                } catch (e : Exception){
                    coroutileDétailÉvenement?.cancel()
                    vue.afficherToastErreurServeur()
                    Log.e("Évèn", "Erreur d'accès à l'API")
                }
            } else {
                try {
                    reponseApi = modèleÉvénements.retirerParticipation(utilisateurÉvenement)

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            participation = false
                            vue.afficherParticipation()
                            vue.afficherToastParticipationRetiré()
                            vue.afficherApplicationCalendrierPourEffacer(setDatePourCalendrier())
                        } else {
                            vue.afficherToastErreurServeur()
                            Log.e(
                                "Évèn",
                                "Le serveur a retourné une erreur"
                            )
                        }
                    }
                } catch (e : Exception){
                    coroutileDétailÉvenement?.cancel()
                    vue.afficherToastErreurServeur()
                    Log.e("Évèn", "Erreur d'accès à l'API")
                }
            }
        }
    }

    override fun traiterRequêteAfficherParticipants() {
        var liste : List<Utilisateur> = ArrayList<Utilisateur>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = modèleUtilisateurs.getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement)
                withContext(Dispatchers.Main) {
                    if (liste.isNotEmpty()) {
                        vue.afficherListeParticipants(liste,{ i -> modèleUtilisateurs.getImageUtilisateur(i)})
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
                liste = modèleÉvénements.getCommentairesDansÉvénement(idEvenement)
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