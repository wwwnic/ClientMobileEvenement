package com.even.présentation.présenteur

import android.util.Log
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.modèle.ModèleMesÉvènements
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*

class PrésentateurMesÉvènements(
    val vue: IMesÉvènements.IVue,
) : IMesÉvènements.IPrésentateur {

    private var coroutileParticipation: Job? = null
    private var coroutileÉvènements: Job? = null


    override fun traiterRequêteAfficherLesParticipations() {
        coroutileÉvènements?.cancel()
        val idUtilisateur = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!
        coroutileParticipation = CoroutineScope(Dispatchers.IO).launch {
            try {
                val lstÉvènement =
                    ModèleMesÉvènements().demanderLesParticipations(idUtilisateur)
                withContext(Dispatchers.Main) {
                    if (lstÉvènement.isNotEmpty()) {
                        vue.afficherListeEvenements(
                            lstÉvènement
                        ) { i -> ModèleÉvénements().getImageÉvénement(i) }
                        Log.i("Évèn", "Affichage des évènements")
                    } else {
                        vue.afficherAucunRésultatRecherche(estErreurConnexion = false)
                        Log.e("Évèn", "Aucun évènement")
                    }
                }
            } catch (e: Exception) {
                vue.afficherAucunRésultatRecherche(estErreurConnexion = true)
                Log.e("Évèn", "La requête a rencontré une erreur", e)
            }
        }
    }


    override fun traiterRequêteAfficherSesPropreÉvènements() {
        coroutileParticipation?.cancel()
        val idUtilisateur = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!
        coroutileÉvènements = CoroutineScope(Dispatchers.IO).launch {
            try {
                val lstÉvènement =
                    ModèleMesÉvènements().demanderSesPropreÉvènement(idUtilisateur)
                withContext(Dispatchers.Main) {
                    if (lstÉvènement.isNotEmpty()) {
                        vue.afficherListeEvenements(
                            lstÉvènement
                        ) { i -> ModèleÉvénements().getImageÉvénement(i) }
                        Log.i("Évèn", "Affichage des évènements")
                    } else {
                        vue.afficherAucunRésultatRecherche(estErreurConnexion = false)
                        Log.e("Évèn", "Aucun évènement")
                    }
                }
            } catch (e: Exception) {
                vue.afficherAucunRésultatRecherche(estErreurConnexion = true)
                Log.e("Évèn", "La requête a rencontré une erreur", e)
            }
        }
    }

}