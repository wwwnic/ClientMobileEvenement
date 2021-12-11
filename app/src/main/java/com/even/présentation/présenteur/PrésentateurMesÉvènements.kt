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