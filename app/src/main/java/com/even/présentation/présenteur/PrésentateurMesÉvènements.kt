package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*

class PrésentateurMesÉvènements(
    val vue: IMesÉvènements.IVue,
) : IMesÉvènements.IPrésentateur {

    private var coroutine: Job? = null


    override fun traiterRequêtelancerCoroutine(estSurOngletMesÉvènement: Boolean) {
        val idUtilisateur = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!
        coroutine?.cancel()
        coroutine = CoroutineScope(Dispatchers.IO).launch {
            try {
                val lstÉvènement = if (estSurOngletMesÉvènement) {
                    ModèleÉvénements().demanderSesPropreÉvènement(idUtilisateur)
                } else {
                    ModèleÉvénements().demanderLesParticipations(idUtilisateur)
                }
                withContext(Dispatchers.Main) {
                    afficherlstÉvènement(lstÉvènement)
                }
            } catch (e: Exception) {
                vue.afficherAucunRésultatRecherche(estErreurConnexion = true)
                Log.e("Évèn", "La requête a rencontré une erreur", e)
            }
        }
    }

    private fun afficherlstÉvènement(lstÉvènement: List<Événement>) {
        if (lstÉvènement.isNotEmpty()) {
            vue.afficherListeEvenements(
                lstÉvènement
            ) { i -> ModèleÉvénements().getImageÉvénement(i) }
        } else {
            vue.afficherAucunRésultatRecherche(estErreurConnexion = false)
        }
    }
}