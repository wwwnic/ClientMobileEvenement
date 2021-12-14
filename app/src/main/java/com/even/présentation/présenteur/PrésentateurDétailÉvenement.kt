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

/**
 * Permet de faire les traitements dans la vue de détail d'un événement.
 *
 * @property vue La vue VueDétailsÉvénement
 * @property modeleAuthentification Le modèle d'authentification
 * @property modèleUtilisateurs Le modèle d'utilisateur
 * @property modèleÉvénements Le modèle d'événement
 * @property dispatcher Le contexte pour les coroutines
 */
@testOuvert
class PrésentateurDétailÉvenement(
    var vue: IDétailÉvenement.IVue,
    var modeleAuthentification : ModèleAuthentification,
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

    /**
     * Cette méthode permet d'interroger les différents modèles afin d'aller chercher les informations
     * d'un événement. C'est cette méthode qui s'occupera de l'affichage des détails de l'événement
     * lorsqu'un événement est sélectionné.
     *
     * @param idEvenement C'est la clé unique qui identifie l'événement.
     */
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

    /**
     * Cette méthode s'occupe d'ajouter ou retirer la participation d'un utilisateur en fonction
     * de son status de participation qui est initialisé lorsque la vue est chargé.     *
     *
     * @param idEvenement C'est la clé unique qui identifie l'événement.
     */
    override fun traiterRequêteAjouterRetirerParticipation(idEvenement: Int) {
        var reponseApi : Response<Void>
        val utilisateurÉvenement = UtilisateurÉvénement(idUtilisateurConnecté, idEvenement)

        coroutileDétailÉvenement = CoroutineScope(Dispatchers.IO).launch {

            if (participation == false) {
                try {
                    reponseApi = modèleÉvénements.ajouterParticipation(utilisateurÉvenement)
                    nombreParticipant = modèleUtilisateurs.getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement).count()

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            participation = true
                            vue.afficherNePlusParticiper()
                            vue.setNombreParticipant(nombreParticipant)
                            vue.afficherToastParticipationAjouté()
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
                    nombreParticipant = modèleUtilisateurs.getUtilisateursDansÉvénement(evenementEnCours!!.idEvenement).count()

                    withContext(Dispatchers.Main) {
                        if (reponseApi.isSuccessful) {
                            participation = false
                            vue.afficherParticipation()
                            vue.setNombreParticipant(nombreParticipant)
                            vue.afficherToastParticipationRetiré()
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

    /**
     * Passe la date à la vue pour pouvoir ouvrir l'application calendrier.
     */
    override fun traiterRequêteAjouterAuCalendrier() {
        vue.afficherApplicationCalendrierPourAjouter(setDatePourCalendrier())
    }

    /**
     * Permet d'aller chercher la liste de participant à partir du modèle et de la passer
     * à la vue pour en faire l'affichage.
     *
     */
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

    /**
     * Permet d'aller chercher la liste de commentaire à partir du modèle et de la passer
     * à la vue pour en faire l'affichage.
     *
     */
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

    /**
     * Permet de faire la vérification pour voir si l'utilisateur sélectionné participe
     * ou ne participe pas à l'événement en cours. À partir de la variable de classe participation,
     * le texte du bouton de participation sera changé dans la vue.
     *
     */
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

    /**
     * Permet de changer la date en tableau.
     *
     * @return Retourne la date sous forme de tableau.
     */
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