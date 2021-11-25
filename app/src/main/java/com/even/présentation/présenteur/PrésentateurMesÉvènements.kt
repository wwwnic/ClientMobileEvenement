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

    override fun traiterRequêteAfficherLesParticipations() {
        val idUtilisateur = ModèleConnexion.utilisateurConnecté?.idUtilisateur!!

        coroutileParticipation = CoroutineScope(Dispatchers.IO).launch {
            try {
                val lstÉvènement =
                    ModèleMesÉvènements().demanderLesParticipationsParId(idUtilisateur)
                withContext(Dispatchers.Main) {
                    if (lstÉvènement.size != 0) {
                        vue.afficherListeEvenements(
                            lstÉvènement,
                            { i -> ModèleÉvénements().getImageÉvénement(i) })
                        Log.i("Évèn", "Affichage des évènements")
                    } else {
                        //todo:afficher aucun évènements
                        Log.e("Évèn", "Aucun évènement")

                    }
                }
            } catch (e: Exception) {
                //todo:afficher aucun évènements AVEC toast erreur serveur
                Log.e("Évèn", "La requête a rencontré une erreur", e)
            }
        }


    }

}