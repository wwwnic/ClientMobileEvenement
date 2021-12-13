package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class PrésentateurMesÉvènements(
    val vue: IMesÉvènements.IVue,
) : IMesÉvènements.IPrésentateur {

    private var coroutine: Job? = null

    val modèleÉvénements = ModèleÉvénements()

    /**
     * Lance une coroutine pour recuperer ses évènements ou ses participations selon l'onglet
     *
     * @param estSurOngletMesÉvènement est vrai si l'utilisateur est sur l'onglet mes évènements
     */
    override fun traiterRequêtelancerCoroutine(estSurOngletMesÉvènement: Boolean) {
        val idUtilisateur = ModèleAuthentification.utilisateurConnecté?.idUtilisateur!!
        coroutine?.cancel()
        coroutine = CoroutineScope(Dispatchers.IO).launch {
            try {
                val lstÉvènement = if (estSurOngletMesÉvènement) {
                    modèleÉvénements.demanderSesPropreÉvènement(idUtilisateur)
                } else {
                    modèleÉvénements.demanderLesParticipations(idUtilisateur)
                }
                withContext(Dispatchers.Main) {
                    afficherlstÉvènement(lstÉvènement)
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(Dispatchers.Main) {
                    vue.afficherAucunRésultatRecherche(estErreurConnexion = true)
                }
            }
        }
    }

    /**
     * Affiche l'évènement que l'utilisateur à choisi
     *
     * @param idÉvénement l'id de l'évènement selectionné
     */
    override fun traiterRequêteAfficherÉvénement(idÉvénement: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                modèleÉvénements.setÉvénementPrésenté(idÉvénement)
                withContext(Dispatchers.Main) {
                    vue.afficherÉvénementSelectionné()
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherAucunRésultatRecherche(estErreurConnexion = true)
                }
            }
        }
    }

    /**
     * Permets d'afficher une liste d'évènement quelconque
     *
     * @param lstÉvènement Une liste d'évènement à afficher
     */
    private fun afficherlstÉvènement(lstÉvènement: List<Événement>) {
        if (lstÉvènement.isNotEmpty()) {
            vue.afficherListeEvenements(
                lstÉvènement
            ) { i -> modèleÉvénements.getImageÉvénement(i) }
        } else {
            vue.afficherAucunRésultatRecherche(estErreurConnexion = false)
        }
    }
}